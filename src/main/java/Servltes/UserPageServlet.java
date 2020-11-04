package Servltes;

import DBObjects.UsersProfilesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/user")
public class UserPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet:'/user' Param 'get': "+req.getParameter("get"));
        if (req.getParameter("get") != null) {
            HttpSession session = req.getSession();

            String userProfileJson = "";
            System.out.println("Servlet:'/user' Param 'id': "+req.getParameter("id"));
            System.out.println("Session get profileID: "+(int) session.getAttribute("profileID"));
            if (req.getParameter("id") == "") {
                userProfileJson = UsersProfilesJDBC.here.getJSON(
                        (int) session.getAttribute("profileID"));
            } else {
                userProfileJson = UsersProfilesJDBC.here.getJSON(
                        Integer.parseInt(req.getParameter("id")));
            }
            System.out.println("UserProfileJSON: "+userProfileJson.substring(0, 20)+" ...");

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(userProfileJson);
            resp.getWriter().flush();
        } else {
            Properties prop = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("paths.properties");
            prop.load(input);
            req.getRequestDispatcher(prop.getProperty("userPage")).forward(req, resp);
        }
    }
}
