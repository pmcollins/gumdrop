package gumdrop.web.html;

import gumdrop.web.http.HttpStringUtil;

public class Text implements Buildable {

  private final String text;

  Text(String text) {
    this.text = HttpStringUtil.stripTags(text);
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(text);
  }

}
