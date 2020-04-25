package gumdrop.web.html;

import java.util.Objects;

public class TagAttribute implements Buildable {

  private static final String EQUALS_QUOTE = "=\"";
  private static final char SPACE = ' ';
  private static final char QUOTE = '"';

  private final Buildable k;
  private final Buildable v;

  // visible for testing
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

  @Override
  public String toString() {
    return "TagAttribute{k=" + k + ", v=" + v + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TagAttribute that = (TagAttribute) o;
    return k.equals(that.k) &&
      v.equals(that.v);
  }

  @Override
  public int hashCode() {
    return Objects.hash(k, v);
  }

}
