package CustomTags.IndexTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class SideBarBtnTag extends SimpleTagSupport {

    private String text;
    private StringWriter body;
    private String oninputFunctionName;

    public void setText(String text) {
        this.text = text;
    }

    public void setOninputFunctionName(String oninputFunctionName) {
        this.oninputFunctionName = oninputFunctionName;
    }

    public SideBarBtnTag() {
        super();
        body = new StringWriter();
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            getJspBody().invoke(body);
            getJspContext().getOut().println(
                    "<button class=\"toggle-btn\" " +
                            "onclick=\"" + oninputFunctionName + "\">" +
                            body.toString() +
                            "<p>" + text + "</p>" +
                            "</button>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
