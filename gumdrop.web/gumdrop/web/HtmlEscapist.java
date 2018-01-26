package gumdrop.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEscapist {

  private static final Pattern PATTERN = Pattern.compile("%([0-9a-fA-F][0-9a-fA-F])");

  public static String unescape(String queryStr) {
    // consolidate?
    String replaced = queryStr.replaceAll("\\+", " ");
    Matcher matcher = PATTERN.matcher(replaced);
    StringBuilder sb = new StringBuilder();
    while (matcher.find()) {
      matcher.appendReplacement(sb, parseEntity(matcher.group()));
    }
    matcher.appendTail(sb);
    return sb.toString();

  }

  public static String parseEntity(String entity) {
    int num = Integer.parseInt(entity.substring(1), 16);
    return String.valueOf((char) num);
  }

}
