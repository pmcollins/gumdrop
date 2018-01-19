package gumdrop.web;

import static gumdrop.web.TagLib.*;

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
  public void build(StringBuilder sb) {
    new LabeledWidget(
      label,
      input().attr("type", type).attr("name", name)
    ).build(sb);
  }

}