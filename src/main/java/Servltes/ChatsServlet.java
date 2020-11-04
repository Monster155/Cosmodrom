package Servltes;

import DBObjects.ChatsJDBC;
import DBObjects.MessagesJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        if (req.getParameter("update") != null) {
            System.out.println("update");
            Integer messageID = Integer.parseInt(req.getParameter("messageID"));
            Integer senderID = Integer.parseInt(req.getParameter("senderID"));
            Integer chatID = Integer.parseInt(req.getParameter("chatID"));
            String text = req.getParameter("text");
            if (req.getSession().getAttribute("profileID").equals(senderID))
                MessagesJDBC.here.update(messageID, text, chatID);
        } else {
            if (req.getParameter("delete") != null) {
                System.out.println("delete");
                Integer messageID = Integer.parseInt(req.getParameter("messageID"));
                Integer senderID = Integer.parseInt(req.getParameter("senderID"));
                Integer chatID = Integer.parseInt(req.getParameter("chatID"));
                if (req.getSession().getAttribute("profileID").equals(senderID))
                    MessagesJDBC.here.delete(messageID, chatID);
            } else {
                System.out.println("add");
                String chatID = req.getParameter("chatID");
                String text = req.getParameter("text");
                String profileID = req.getParameter("profileID");
                System.out.println("(ChatsServlet#add) chatID='" + chatID + " profileID=" + profileID + "' text='" + text + "'");
                MessagesJDBC.here.add(
                        Integer.parseInt(chatID),
                        text.trim(),
                        Integer.parseInt(profileID)
                );
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // only logs
        if (req.getParameter("sync") == null) {
            System.out.print("Servlet:'/m' Params:");
            if (!req.getParameterMap().isEmpty()) {
                req.getParameterMap().forEach((s, strings) -> {
                    System.out.print("'" + s + "'=[");
                    for (String str : strings) {
                        System.out.print(str + ",");
                    }
                    System.out.println("]");
                });
            } else System.out.println();
        }
        if (req.getParameter("sync") != null) {
            int chatID = Integer.parseInt(req.getParameter("chatID"));
            // make response shorter
            Integer messagesFront = Integer.parseInt(req.getParameter("sync"));
            Integer messagesBack = MessagesJDBC.here.getMessagesCount(chatID);

//            System.out.println("/m messagesFront: " + messagesFront);
//            System.out.println("/m messagesBack: " + messagesBack);
            if (messagesBack > messagesFront) {
                int limit = messagesBack - messagesFront;
                System.out.println("/m limit: " + limit);
                ArrayList<MessagesJDBC.Message> messages = MessagesJDBC.here.getMessages(chatID, limit);
//                if (ChatsJDBC.here.getChatUsersCount(chatID) > 2) {
//                    ArrayList<UsersProfilesJDBC.UserProfile> userProfiles = ChatsJDBC.here.getChatUsers(chatID);
//                }
                for (MessagesJDBC.Message message : messages) {
                    System.out.println(message.getId() + " " + messages.indexOf(message));
                }

                StringBuilder textToPrint = new StringBuilder();
                for (MessagesJDBC.Message message : messages) {
                    textToPrint.append(getChatElementDiv(
                            message.getText(),
                            message.getId(),
                            message.getSender_id(),
                            message.getTimestamp()));
                }
                System.out.println(textToPrint.toString());
                resp.getWriter().write(textToPrint.toString());
                resp.getWriter().flush();
            }
        } else {
            if (req.getParameter("id") != null) {
                System.out.println("Servlet:'/m' Param 'id': " + req.getParameter("id"));
                Integer otherID = Integer.parseInt(req.getParameter("id"));
                Integer ownerID = (Integer) session.getAttribute("profileID");
                System.out.println("Servlet:'/m' Params: 'otherID'=" + otherID + " 'ownerID'=" + ownerID);

                int chatID;
                if (otherID == ownerID) {
                    //chat with yourself
                    chatID = ChatsJDBC.here.getChat(new int[]{ownerID});
                } else {
                    chatID = ChatsJDBC.here.getChat(new int[]{ownerID, otherID});
                }
                resp.sendRedirect("/m?chatID=" + chatID);
            } else {
                Properties prop = new Properties();
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream input = classLoader.getResourceAsStream("paths.properties");
                prop.load(input);
                req.getRequestDispatcher(prop.getProperty("chats")).forward(req, resp);
            }
        }
    }

    private String getChatElementDiv(String text, int messageID, int senderID, Timestamp timestamp) {
        return "<div class='message' messageID='" + messageID + "' senderID='" + senderID + "' onclick='selectMessage(this)'>" +
                "<div class='message-text'>" + text + "</div>" +
                "<div class='timestamp'>" + new SimpleDateFormat("hh:mm").format(timestamp) + "</div>" +
                "</div>";
    }
}

