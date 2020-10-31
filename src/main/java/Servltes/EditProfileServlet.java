package Servltes;

import DBObjects.UsersProfilesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/reg")
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String description = req.getParameter("description");

        Part filePart = req.getPart("photo");
        InputStream fileContent = filePart.getInputStream();

        int userID = (Integer) session.getAttribute("userID");
        int userProfileID = UsersProfilesJDBC.here.add(userID, name, surname, description, fileContent);

        session.setAttribute("profileID", userProfileID);

        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
    }
}
