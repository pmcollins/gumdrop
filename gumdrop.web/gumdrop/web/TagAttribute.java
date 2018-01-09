package gumdrop.web;

public class TagAttribute implements Buildable {

  private static final String EQUALS_QUOTE = "=\"";
  private static final char SPACE = ' ';
  private static final char QUOTE = '"';

  private final Buildable k;
  private final Buildable v;

  public TagAttribute(String key, String value) {
    k = new Text(key);
    v = new Text(value);
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(SPACE);
    k.build(sb);
    sb.append(EQUALS_QUOTE);
    v.build(sb);
    sb.append(QUOTE);
  }

}
