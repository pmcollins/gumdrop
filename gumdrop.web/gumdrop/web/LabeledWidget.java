package gumdrop.web;

import static gumdrop.web.TagLib.div;
import static gumdrop.web.TagLib.label;
import static gumdrop.web.TagLib.text;

class LabeledWidget extends Widget {

  private final String label;
  private final Buildable widget;

  LabeledWidget(String label, Buildable widget) {
    this.label = label;
    this.widget = widget;
  }

  @Override
  protected Buildable getRoot() {
    return div(
      label(
        text(label),
        widget
      )
    );
  }

}
