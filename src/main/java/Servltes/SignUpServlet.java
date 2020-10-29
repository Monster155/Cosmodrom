package Servltes;

import DBObjects.Users;

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
            if (!Users.here.contains(email)) {
                if (email.matches("([0-9a-zA-Z]+[\\.\\-_]?)*[0-9a-zA-Z]+@([0-9a-zA-Z]+[\\.\\-_]?)+\\.[0-9a-zA-Z]+")
                        && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {

                    Users.here.add(email, password);

                    session.setAttribute("email", email);
                    session.setAttribute("password", password);
                    session.setAttribute("auth", true);

                    req.getRequestDispatcher(prop.getProperty("main")).forward(req, resp);
                } else req.getRequestDispatcher(prop.getProperty("index") + "?return=-13").forward(req, resp);
            } else req.getRequestDispatcher(prop.getProperty("index") + "?return=-12").forward(req, resp);
        } else req.getRequestDispatcher(prop.getProperty("index") + "?return=-11").forward(req, resp);
    }
}
