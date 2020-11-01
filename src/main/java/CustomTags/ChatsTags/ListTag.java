package CustomTags.ChatsTags;

import DBObjects.ChatsJDBC;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class ListTag extends SimpleTagSupport {

    private StringWriter body;

    public ListTag() {
        super();
        body = new StringWriter();
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(body);
        System.out.println(body.toString().trim());

        ArrayList<ChatsJDBC.Chat> chats = ChatsJDBC.here.getUserChats(Integer.parseInt(body.toString().trim()));
        StringBuilder textToPrint = new StringBuilder();
        for (ChatsJDBC.Chat chat : chats) {
            textToPrint.append(getChatElementDiv(chat.getName(), chat.getId()));
        }

        try {
            getJspContext().getOut().println(textToPrint.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getChatElementDiv(String chatName, int chatID) {
        //TODO make substring+"..." if name length > 10
        if (chatName.length() > 18) {
            chatName = chatName.substring(0, 15) + "...";
        }
        return "<div " +
                "class='chatElement' " +
                "chatID='" + chatID + "'"  +
                "onclick='replaceURL(this)'><div>" + chatName + "</div></div>";
    }
}
