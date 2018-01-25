package gumdrop.web;

import static gumdrop.web.TagLib.textarea;

public class LabeledTextArea extends Widget {

  private final String name;
  private final String label;
  private final int rows;

  public LabeledTextArea(Enum<?> name, String label, int rows) {
    this(name.toString().toLowerCase(), label, rows);
  }

  private LabeledTextArea(String name, String label, int rows) {
    this.name = name;
    this.label = label;
    this.rows = rows;
  }

  @Override
  protected Buildable getBuildable() {
    Tag textArea = textarea().attr(
      "name", name
    ).attr(
      "rows", String.valueOf(rows)
    );
    return new LabeledWidget(label, textArea);
  }

}
