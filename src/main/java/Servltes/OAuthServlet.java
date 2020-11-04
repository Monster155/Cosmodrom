package Servltes;

import DBObjects.UsersLoginJDBC;
import DBObjects.UsersProfilesJDBC;
import OAuthServices.OAuthUser;
import OAuthServices.VK;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@WebServlet("/oauth")
public class OAuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        if (req.getParameter("code") != null) {
            String code = req.getParameter("code");

            OAuthUser oAuthUser = null;
            try {
                oAuthUser = VK.here.getUser(code);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            String email = oAuthUser.email;
            System.out.println("Logined oauth user: " + email);

            int userID = UsersLoginJDBC.here.validate(email);
            if (userID != -1) {
                session.setAttribute("profileID", UsersProfilesJDBC.here.getUserProfileID(userID));
            } else {
                String name = rusToEng(oAuthUser.first_name);
                String surname = rusToEng(oAuthUser.last_name);
                String description = "";

                InputStream fileContent = new URL(oAuthUser.photo_200).openStream();

                System.out.println(name + " " + surname + " " + description + " " + email);
                userID = UsersLoginJDBC.here.add(email);
                int userProfileID = UsersProfilesJDBC.here.add(userID, name, surname, description, fileContent);

                session.setAttribute("profileID", userProfileID);
            }
            session.setAttribute("auth", true);
            session.setAttribute("userID", userID);

            req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
        } else {
            resp.sendRedirect(VK.here.getURL());
        }

    }

    private String getData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder sb = new StringBuilder();
        int charByte;
        while ((charByte = bufferedReader.read()) != -1) {
            sb.append((char) charByte);
        }
        return sb.toString();
    }

    private String rusToEng(String message) {
        char[] abcCyr = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш'};
        String[] abcLat = {"a", "b", "v", "g", "d", "e", "zh", "z", "i", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "A", "B", "V", "G", "D", "E", "Zh", "Z", "I", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "C", "Ch", "Sh"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

}
