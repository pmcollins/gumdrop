package gumdrop.web;

import static gumdrop.web.TagLib.input;

abstract class LabeledInputWidget extends Widget {

  private final String type;
  private final String label;
  private final String name;

  LabeledInputWidget(String type, Enum<?> name, String label) {
    this(type, name.toString().toLowerCase(), label);
  }

  LabeledInputWidget(String type, String name, String label) {
    this.type = type;
    this.label = label;
    this.name = name;
  }

  @Override
  protected Buildable getRoot() {
    return new LabeledWidget(
      label,
      input().attr("type", type).attr("name", name)
    );
  }

}
