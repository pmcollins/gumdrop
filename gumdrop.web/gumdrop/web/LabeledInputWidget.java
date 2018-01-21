package gumdrop.web;

import static gumdrop.web.TagLib.input;

abstract class LabeledInputWidget extends Widget {

  private final String type;
  private final String name;
  private final String label;

  LabeledInputWidget(String type, Enum<?> name, String label) {
    this(type, name.toString().toLowerCase(), label);
  }

  LabeledInputWidget(String type, String name, String label) {
    this.type = type;
    this.name = name;
    this.label = label;
  }

  @Override
  protected Buildable getBuildable() {
    return new LabeledWidget(
      label,
      input().attr("type", type).attr("name", name)
    );
  }

}
