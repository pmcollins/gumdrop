package gumdrop.web;

import static gumdrop.web.TagLib.form;

public class FormWidget extends Widget {

  private final String formAction;
  private final ButtonPanelWidget buttonPanelWidget;
  private Buildable[] children;
  private final Tag form;

  public FormWidget(String formAction, String submitText) {
    this(formAction, new ButtonPanelWidget(submitText));
  }

  public FormWidget(String formAction, ButtonPanelWidget buttonPanelWidget) {
    this.formAction = formAction;
    this.buttonPanelWidget = buttonPanelWidget;
    form = form().attr("method", "post");
  }

  public void setChildren(Buildable... children) {
    this.children = children;
  }

  @Override
  protected Buildable getBuildable() {
    form.attr("action", formAction);
    form.attr("class", "grid");
    if (children != null) {
      form.add(children);
    }
    form.add(buttonPanelWidget);
    return form;
  }

}
