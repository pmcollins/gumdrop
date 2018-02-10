package gumdrop.web;

import static gumdrop.web.TagLib.form;

public class FormWidget extends Widget {

  private final String formAction;
  private final ButtonPanelWidget buttonPanelWidget;
  private Buildable[] children;
  private final Tag form;
  private boolean suppressGrid;

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

  public void attr(String name, String val) {
    form.attr(name, val);
  }

  public void suppressGrid() {
    suppressGrid = true;
  }

  @Override
  protected Buildable getBuildable() {
    attr("action", formAction);
    if (!suppressGrid) {
      attr("class", "grid");
    }
    if (children != null) {
      form.add(children);
    }
    form.add(buttonPanelWidget);
    return form;
  }

}
