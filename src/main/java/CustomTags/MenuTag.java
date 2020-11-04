package CustomTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class MenuTag extends SimpleTagSupport {

    StringWriter body;

    public MenuTag() {
        super();
        body = new StringWriter();
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(body);
        String profileID = body.toString().trim();
        try {
            getJspContext().getOut().print(
                    "<div id='menu'>" +
                            "   <form action='/' method='get'>" +
                            "       <button type='submit' id='logo' class='menu-item'>" +
                            "           <img src='/imgs/Logo2.png' id='logo-img'>" +
                            "       </button>" +
                            "   </form>" +
                            "   <form action='/m' method='get'>" +
                            "       <button type='submit' id='menu-chats' class='menu-item'>" +
                            "           <h2>Chats</h2>" +
                            "       </button>" +
                            "   </form>" +
                            "   <form action='/user' method='get'>" +
                            "       <input type='text' name='id' value='" + profileID + "' style='display: none'>" +
                            "       <button type='submit' id='menu-profile' class='menu-item'>" +
                            "           <h2>Profile</h2>" +
                            "       </button>" +
                            "   </form>" +
                            "   <form action='/search' method='get'>" +
                            "       <button type='submit' id='menu-search' class='menu-item'>" +
                            "           <h2>Search</h2>" +
                            "       </button>" +
                            "   </form>" +
                            "   <form action='/out' method='post'>" +
                            "       <button type='submit' id='menu-exit' class='menu-item'>" +
                            "           <h2>Exit</h2>" +
                            "       </button>" +
                            "   </form>" +
                            "</div>");
        } catch (IOException e) {
            System.out.println("(MenuTag) " + e.getMessage() + " " + e.getCause());
        }
    }
}
