package Servltes;

import DBObjects.UsersLoginJDBC;
import RestoreSendingEmail.SendingEmail;

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
        req.getRequestDispatcher(prop.getProperty("restore")).forward(req, resp);

        int profileID = UsersLoginJDBC.here.getUser(email);
        System.out.println(profileID);
        if (profileID > 0) {
            String newPass = UsersLoginJDBC.here.restore(profileID);
            SendingEmail.here.sendMessage("Restore Password", "Your new password " + newPass, email);
            System.out.println("Sending email...");
        }
    }
}
