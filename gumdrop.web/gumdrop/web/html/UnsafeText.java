package gumdrop.web.html;

public class UnsafeText implements Buildable {

  private final String text;

  public UnsafeText(String text) {
    this.text = text;
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(text);
  }

}
