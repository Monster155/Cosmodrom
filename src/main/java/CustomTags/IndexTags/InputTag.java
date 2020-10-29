package CustomTags.IndexTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class InputTag extends SimpleTagSupport {

    private StringWriter body;
    private String id;
    private String inputType;
    private String inputPlaceholder;
    private String inputName;
    private String inputID;


    public InputTag() {
        super();
        body = new StringWriter();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setInputPlaceholder(String inputPlaceholder) {
        this.inputPlaceholder = inputPlaceholder;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            getJspBody().invoke(body);
            getJspContext().getOut().println(
                    "<div class='input-area' id='" + id + "'>" +
                            body.toString() +
                            "<input type='" + inputType + "' placeholder='" + inputPlaceholder + "' name='" + inputName + "' id='" + inputID + "'>" +
                            "</div>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
