package Servltes;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@WebServlet("/reg")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//TODO save to usersLogin from session
        // and to usersProfiles
        System.out.println(req.getParameter("fileToUpload"));

        try {
            // retrieve image
            BufferedImage bi = ImageIO.read(new URL(req.getParameter("fileToUpload")));
            File outputFile = new File("/imgs/saved.png");
            ImageIO.write(bi, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

//        req.getRequestDispatcher(prop.getProperty("userPage")).forward(req, resp);
    }
}
