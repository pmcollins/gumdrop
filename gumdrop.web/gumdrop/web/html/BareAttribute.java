package gumdrop.web.html;

public class BareAttribute implements Buildable {

  private final Buildable text;

  BareAttribute(String name) {
    text = new Text(name);
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(' ');
    text.build(sb);
  }

}
