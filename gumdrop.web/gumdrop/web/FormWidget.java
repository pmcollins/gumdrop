package gumdrop.web;

import static gumdrop.web.TagLib.div;
import static gumdrop.web.TagLib.form;

public class FormWidget extends Widget {

  private final String formAction;
  private Buildable[] children;
  private ButtonPanel buttonPanel;

  public FormWidget(String formAction, String submitText) {
    this.formAction = formAction;
    buttonPanel = new ButtonPanel(submitText);
  }

  public FormWidget(String formAction, ButtonPanel buttonPanel) {
    this.formAction = formAction;
    this.buttonPanel = buttonPanel;
  }

  public void setChildren(Buildable... children) {
    this.children = children;
  }

  @Override
  protected Buildable getBuildable() {
    Tag form = form().attr("method", "post").attr("action", formAction);
    if (children != null) {
      form.add(children);
    }
    form.add(buttonPanel);
    return div(form);
  }

}

