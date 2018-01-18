package gumdrop.web;

import static gumdrop.web.TagLib.textarea;

public class TextArea extends Widget {

  private final String name;
  private final int rows;
  private final int cols;

  public TextArea(Enum<?> name, int rows, int cols) {
    this(name.toString().toLowerCase(), rows, cols);
  }

  private TextArea(String name, int rows, int cols) {
    this.name = name;
    this.rows = rows;
    this.cols = cols;
  }

  @Override
  public void build(StringBuilder sb) {
    textarea().attr(
      "name", name
    ).attr(
      "rows", String.valueOf(rows)
    ).attr(
      "cols", String.valueOf(cols)
    ).build(sb);
  }

}
