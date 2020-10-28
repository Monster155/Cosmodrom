package CustomTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class IndexContainerBtnTag extends SimpleTagSupport {

    private StringWriter body;
    private String text;

    public IndexContainerBtnTag() {
        super();
        body = new StringWriter();
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            getJspBody().invoke(body);
            getJspContext().getOut().println(
                    "<button type='submit' class='submit-button'>" +
                            body +
                            "<div>" + text + "</div>" +
                            "</button>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
