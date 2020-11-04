package CustomTags.ChatsTags;

import DBObjects.ChatsJDBC;
import DBObjects.MessagesJDBC;
import DBObjects.UsersProfilesJDBC;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessagesTag extends SimpleTagSupport {

    private StringWriter body;

    public MessagesTag() {
        super();
        body = new StringWriter();
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(body);
        String chatIDStr = body.toString().trim();
        System.out.println("ChatID in MessageTag: " + chatIDStr);
        if (chatIDStr.equals("")) {
            return;
        }
        int chatID = Integer.parseInt(chatIDStr);

        ArrayList<MessagesJDBC.Message> messages = MessagesJDBC.here.getMessages(chatID, 0);
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

        try {
            getJspContext().getOut().println(textToPrint.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private String getChatElementWithPhotoDiv(String text, int messageID, Photo senderPhoto) {
//        //TODO rewrite this
//        return "<div class='message' messageID='" + messageID + "' onclick='" + "'>" +
//                "<div class='message-img'><img src='" + senderPhoto + "'></div>" +
//                "<div class='message-text'>" + text + "</div>" +
//                "</div>";
//    }

    private String getChatElementDiv(String text, int messageID, int senderID, Timestamp timestamp) {
        return "<div class='message' messageID='" + messageID + "' senderID='" + senderID + "' onclick='selectMessage()'>" +
                "<div class='message-text'>" + text + "</div>" +
                "<div class='timestamp'>" + new SimpleDateFormat("hh:mm").format(timestamp) + "</div>" +
                "</div>";
    }
}
