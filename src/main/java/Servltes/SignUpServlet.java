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

@WebServlet("/up")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password-repeat");

        if (password.equals(password2)) {
            if (!UsersLoginJDBC.here.contains(email)) {
                if (password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$")) {
                    session.setAttribute("email", email);
                    session.setAttribute("password", password);
                    session.setAttribute("auth", true);
                    System.out.println("Registered user: " + email + " " + password);

                    req.getRequestDispatcher(prop.getProperty("editProfile")).forward(req, resp);
                } else {
                    System.out.println("Password doesn't match regex: " + email);
                    resp.sendRedirect(prop.getProperty("index") + "?return=-13");
                }
            } else {
                System.out.println("Entered email contains in DB: " + email);
                resp.sendRedirect(prop.getProperty("index") + "?return=-12");
            }
        } else {
            System.out.println("Entered password don't equal " + password + " " + password2);
            resp.sendRedirect(prop.getProperty("index") + "?return=-11");
        }
    }
}
