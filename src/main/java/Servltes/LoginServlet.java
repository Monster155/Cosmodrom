package Servltes;

import DBObjects.UsersLoginJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/in")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println(email + " " + password);

        int userID = UsersLoginJDBC.here.validate(email, password);
        if (userID != -1) {
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            session.setAttribute("userID", userID);
            session.setAttribute("auth", true);

            req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
        } else {
            resp.sendRedirect(prop.getProperty("index") + "?return=-1");
        }
    }
}
