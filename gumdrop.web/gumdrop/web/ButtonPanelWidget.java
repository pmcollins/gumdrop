package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.web.TagLib.div;

public class ButtonPanelWidget extends Widget {

  private List<Buildable> list = new ArrayList<>();
  private SubmitButtonWidget submitButtonWidget;

  public ButtonPanelWidget(String submitText) {
    submitButtonWidget = new SubmitButtonWidget(submitText);
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
    out.add(submitButtonWidget);
    return out;
  }

  public void disableSubmit(boolean disable) {
    submitButtonWidget.disable(disable);
  }

}
