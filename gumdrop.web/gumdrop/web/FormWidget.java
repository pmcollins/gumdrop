package gumdrop.web;

import static gumdrop.web.TagLib.div;
import static gumdrop.web.TagLib.form;

public class FormWidget extends Widget {

  private final String formAction;
  private final String submitText;
  private Buildable[] children;

  public FormWidget(String formAction, String submitText) {
    this.formAction = formAction;
    this.submitText = submitText;
  }

  public void setChildren(Buildable... children) {
    this.children = children;
  }

  @Override
  protected Buildable getBuildable() {
    Tag form = form().attr("method", "post").attr("action", formAction);
    form.add(children);
    form.add(div(new SubmitButton(submitText)).attr("class", "button-panel"));
    return div(form);
  }

}
