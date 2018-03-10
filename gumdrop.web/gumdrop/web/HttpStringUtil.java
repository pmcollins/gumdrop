package gumdrop.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpStringUtil {

  private static final Pattern CHARS = Pattern.compile("%([0-9a-fA-F][0-9a-fA-F])");
  private static final Pattern PLUS = Pattern.compile("\\+");
  private static final Pattern LT = Pattern.compile("<");
  private static final Pattern GT = Pattern.compile(">");

  public static String unescape(String queryStr) {
    String withSpaces = PLUS.matcher(queryStr).replaceAll(" ");
    Matcher matcher = CHARS.matcher(withSpaces);
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

  public static String stripTags(String text) {
    if (text == null) return null;
    return GT.matcher(LT.matcher(text).replaceAll("&lt;")).replaceAll("&gt;");
  }

}
