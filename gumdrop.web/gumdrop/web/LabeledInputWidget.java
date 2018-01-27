package gumdrop.web;

import static gumdrop.web.TagLib.input;

abstract class LabeledInputWidget extends Widget {

  private final String type;
  private final String name;
  private final String label;
  private boolean autofocus;
  private String value;


  LabeledInputWidget(String type, Enum<?> name, String label) {
    this(type, name.toString().toLowerCase(), label);
  }

  LabeledInputWidget(String type, String name, String label) {
    this.type = type;
    this.name = name;
    this.label = label;
  }

  public LabeledInputWidget autofocus() {
    autofocus = true;
    return this;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  protected Buildable getBuildable() {
    Tag input = input().attr("type", type).attr("name", name);
    if (value != null) {
      input.attr("value", value);
    }
    if (autofocus) {
      input.attr("autofocus");
    }
    return new LabeledWidget(label, input);
  }

}
