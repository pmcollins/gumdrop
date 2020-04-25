package gumdrop.web.html;

import gumdrop.web.http.HttpStringUtil;

import java.util.Objects;

public class Text implements Buildable {

  private final String text;

  Text(String text) {
    this.text = HttpStringUtil.sanitizeHtml(text);
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(text);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Text text1 = (Text) o;
    return text.equals(text1.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }

  @Override
  public String toString() {
    return "Text{text='" + text + '\'' + '}';
  }

}
