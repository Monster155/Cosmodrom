package CustomTags.IndexTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class ContainerTag extends SimpleTagSupport {

    private StringWriter body;
    private String method;
    private String action;
    private String groupName;
    private String headText;

    public ContainerTag() {
        super();
        body = new StringWriter();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setHeadText(String headText) {
        this.headText = headText;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            getJspBody().invoke(body);
            getJspContext().getOut().println(
                    "<form method='" + method + "' action='" + action + "'>" +
                            "<div id='" + groupName + "-case' class='input-group'>" +
                            "<div class='head-text'>" + headText + "</div>" +
                            body+
                            "</div>" +
                            "</form>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
