package Servltes;

import DBObjects.UsersLoginJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/rest")
public class RestoreServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        String email = req.getParameter("email");

        if (UsersLoginJDBC.here.contains(email)) {
            // TODO send mail (code like steam guard)
            System.out.println("Sending email...");
            req.getRequestDispatcher(prop.getProperty("restore")).forward(req, resp);
        } else {
            req.getRequestDispatcher(prop.getProperty("restore")).forward(req, resp);
        }
    }
}
