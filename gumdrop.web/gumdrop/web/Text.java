package gumdrop.web;

public class Text implements Buildable {

  private final String text;

  public Text(String text) {
    this.text = text;
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(text);
  }

}
