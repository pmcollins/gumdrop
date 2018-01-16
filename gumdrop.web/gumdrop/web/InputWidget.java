package gumdrop.web;

import static gumdrop.web.TagLib.*;

abstract class InputWidget extends Widget {

  private String type;
  private String label;
  private String name;

  InputWidget(String type, String label, Enum<?> name) {
    this(type, label, name.toString().toLowerCase());
  }

  InputWidget(String type, String label, String name) {
    this.type = type;
    this.label = label;
    this.name = name;
  }

  InputWidget() {
  }

  void setType(String type) {
    this.type = type;
  }

  void setLabel(String label) {
    this.label = label;
  }

  void setName(String name) {
    this.name = name;
  }

  @Override
  public void build(StringBuilder sb) {
    div(
      label(
        text(label),
        input().attr("type", type).attr("name", name)
      )
    ).build(sb);
  }

}
