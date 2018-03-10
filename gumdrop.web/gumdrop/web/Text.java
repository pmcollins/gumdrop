package gumdrop.web;

public class Text implements Buildable {

  private final String text;

  Text(String text) {
    // TODO replace this with a ByteBuilder implementation
    this.text = HttpStringUtil.stripTags(text);
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(text);
  }

}
