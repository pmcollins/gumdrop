package gumdrop.web;

import static gumdrop.web.TagLib.textarea;

public class LabeldTextArea extends Widget {

  private final String name;
  private final String label;
  private final int rows;
  private final int cols;

  public LabeldTextArea(Enum<?> name, String label, int rows, int cols) {
    this(name.toString().toLowerCase(), label, rows, cols);
  }

  private LabeldTextArea(String name, String label, int rows, int cols) {
    this.name = name;
    this.label = label;
    this.rows = rows;
    this.cols = cols;
  }

  @Override
  protected Buildable getBuildable() {
    Tag textArea = textarea().attr(
      "name", name
    ).attr(
      "rows", String.valueOf(rows)
    ).attr(
      "cols", String.valueOf(cols)
    );
    return new LabeledWidget(label, textArea);
  }

}
