package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.web.TagLib.div;

public class ButtonPanel extends Widget {

  private List<Buildable> list = new ArrayList<>();
  private SubmitButton submitButton;

  public ButtonPanel(String submitText) {
    submitButton = new SubmitButton(submitText);
  }

  public void addElement(Buildable buildable) {
    list.add(buildable);
  }

  @Override
  protected Buildable getBuildable() {
    Tag out = div().attr("class", "button-panel");
    for (Buildable buildable : list) {
      out.add(buildable);
    }
    out.add(submitButton);
    return out;
  }

  public void disableSubmit(boolean disable) {
    submitButton.disable(disable);
  }

}
