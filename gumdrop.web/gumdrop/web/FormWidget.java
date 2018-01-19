package gumdrop.web;

import static gumdrop.web.TagLib.div;
import static gumdrop.web.TagLib.form;
import static gumdrop.web.TagLib.input;

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
  protected Buildable getRoot() {
    Tag form = form().attr("method", "post").attr("action", formAction);
    form.add(children);
    form.add(div(input().attr("type", "submit")).attr("value", submitText));
    return div(form);
  }

}
