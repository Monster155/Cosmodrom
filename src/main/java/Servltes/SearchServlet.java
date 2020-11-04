package Servltes;

import DBObjects.UsersProfilesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchText = req.getParameter("searchText");
        if (searchText != null) {
            System.out.println("/search : searchText='" + searchText + "'");
            ArrayList<UsersProfilesJDBC.UserProfile> userProfiles = UsersProfilesJDBC.here.getUsersProfiles(searchText);
            StringBuilder sb = new StringBuilder();
            for (UsersProfilesJDBC.UserProfile userProfile : userProfiles) {
                sb.append(getSearchElementDiv(
                        userProfile.getProfileID(),
                        userProfile.getSurname() + " " + userProfile.getName(),
                        userProfile.getPhoto()));
                System.out.println("User Profiles for Search: " + userProfile.getSurname() + " " + userProfile.getName());
            }
            resp.getWriter().write(sb.toString());
            resp.getWriter().flush();
        } else {
            System.out.println();
            Properties prop = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("paths.properties");
            prop.load(input);
            req.getRequestDispatcher(prop.getProperty("search")).forward(req, resp);
        }
    }

    private String getSearchElementDiv(int profileID, String name, String image) {
        if (!image.equals("")) {
            return "<div class='result' profileID='" + profileID + "' onclick='openProfile(this)'>" +
                    "<div class='result-id'>" + profileID + "</div>" +
                    "<div class='result-realImage' " +
                    "style='background: no-repeat center/cover url(\"data: image/png; base64, " + image + "\");'" +
                    "></div>" +
//                    "<img src='data: image/png; base64, " + image + "' class='result-img'>" +
                    "<div class='result-name'>" + name + "</div>" +
                    "</div>";
        } else {
            return "<div class='result' profileID='" + profileID + "' onclick='openProfile(this)'>" +
                    "<div class='result-id'>" + profileID + "</div>" +
                    "<div class='result-realImage' " +
                    "style='background: no-repeat center/cover url(\"/imgs/space7.jpg\");'" +
                    "></div>" +
                    "<div class='result-name'>" + name + "</div>" +
                    "</div>";
        }
    }
}
