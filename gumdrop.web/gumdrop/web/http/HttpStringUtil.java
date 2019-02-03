package gumdrop.web.http;

public class HttpStringUtil {

  private static final int RADIX = 16;

  public static String unescape(String queryStr) {
    StringBuilder sb = new StringBuilder();
    int length = queryStr.length();
    for (int i = 0; i < length; i++) {
      char c = queryStr.charAt(i);
      if (c == '%' && i < length - 2) {
        char a = queryStr.charAt(i + 1);
        char b = queryStr.charAt(i + 2);
        sb.append(hexToChar(a, b));
        i += 2;
      } else if (c == '+') {
        sb.append(' ');
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  private static char hexToChar(char a, char b) {
    int digitA = Character.digit(a, RADIX);
    int digitB = Character.digit(b, RADIX);
    return (char) ((digitA * RADIX) + digitB);
  }

  public static String parseEntity(String entity) {
    int num = Integer.parseInt(entity.substring(1), RADIX);
    return String.valueOf((char) num);
  }

  public static String sanitizeHtml(String text) {
    if (text == null) return null;
    StringBuilder sb = new StringBuilder();
    int length = text.length();
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if (c == '<') {
        sb.append("&lt;");
      } else if (c == '>') {
        sb.append("&gt;");
      } else if (c == '&') {
        sb.append("&amp;");
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

}
