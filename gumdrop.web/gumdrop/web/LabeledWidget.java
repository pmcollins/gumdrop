package gumdrop.web;

import static gumdrop.web.TagLib.div;
import static gumdrop.web.TagLib.text;

class LabeledWidget extends Widget {

  private final String label;
  private final Buildable widget;

  LabeledWidget(String label, Buildable widget) {
    this.label = label;
    this.widget = widget;
  }

  @Override
  protected Buildable getBuildable() {
    return div(
      div(text(label)).attr("class", "label"),
      div(widget).attr("class", "value")
    ).attr("class", "labeled-widget");
  }

}
