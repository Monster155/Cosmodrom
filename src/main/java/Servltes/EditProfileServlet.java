package Servltes;

import DBObjects.UsersLoginJDBC;
import DBObjects.UsersProfilesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/edit")
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String description = req.getParameter("description");

        Part filePart = req.getPart("photo");
        InputStream fileContent = filePart.getInputStream();

        if (session.getAttribute("profileID") == null) {
            String email = (String) session.getAttribute("email");
            String password = (String) session.getAttribute("password");
            int userID = UsersLoginJDBC.here.add(email, password);
            int userProfileID = UsersProfilesJDBC.here.add(userID, name, surname, description, fileContent);

            session.setAttribute("userID", userID);
            session.setAttribute("profileID", userProfileID);

            req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
        } else {
            int profileID = (Integer) session.getAttribute("profileID");
            if (fileContent.available() > 1)
                UsersProfilesJDBC.here.update(profileID, name, surname, description, fileContent);
            else UsersProfilesJDBC.here.update(profileID, name, surname, description);

            req.getRequestDispatcher(prop.getProperty("userPage")).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("get") != null) {
            HttpSession session = req.getSession();

            String userProfileJson = "";
            int profileID = (int) session.getAttribute("profileID");
            System.out.println("Session get profileID: " + profileID);
            userProfileJson = UsersProfilesJDBC.here.getJSON(profileID);

            System.out.println("UserProfileJSON: " + userProfileJson.substring(0, 20) + " ...");

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(userProfileJson);
            resp.getWriter().flush();
        } else {
            Properties prop = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("paths.properties");
            prop.load(input);
            req.getRequestDispatcher(prop.getProperty("editProfile")).forward(req, resp);
        }
    }
}
