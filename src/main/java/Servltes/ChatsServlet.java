package Servltes;

import DBObjects.ChatsJDBC;
import DBObjects.MessagesJDBC;
import DBObjects.UsersProfilesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet("/m")
public class ChatsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("sync") != null) {
            int chatID = Integer.parseInt(req.getParameter("chat"));

            ArrayList<MessagesJDBC.Message> messages = MessagesJDBC.here.getMessages(chatID);
            if (ChatsJDBC.here.getChatUsersCount(chatID) > 2) {
                ArrayList<UsersProfilesJDBC.UserProfile> userProfiles = ChatsJDBC.here.getChatUsers(chatID);
            }
            StringBuilder textToPrint = new StringBuilder();
            for (MessagesJDBC.Message message : messages) {
                textToPrint.append(getChatElementDiv(
                        message.getText(),
                        message.getId(),
                        message.getSender_id(),
                        message.getTimestamp()));
            }
            resp.getWriter().write(textToPrint.toString());
            resp.getWriter().flush();
        } else {
            Properties prop = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("paths.properties");
            prop.load(input);
            req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
        }
    }

    private String getChatElementDiv(String text, int messageID, int senderID, Timestamp timestamp) {
        return "<div class='message' messageID='" + messageID + "' senderID='" + senderID + "' onclick='selectMessage()'>" +
                "<div class='message-text'>" + text + "</div>" +
                "<div class='timestamp'>" + new SimpleDateFormat("hh:mm").format(timestamp) + "</div>" +
                "</div>";
    }
}

