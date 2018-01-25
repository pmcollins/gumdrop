package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.web.TagLib.div;

public class ButtonPanel extends Widget {

  private List<Buildable> list = new ArrayList<>();
  private String submitText;

  public ButtonPanel(String submitText) {
    this.submitText = submitText;
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
    out.add(new SubmitButton(submitText));
    return out;
  }

}
