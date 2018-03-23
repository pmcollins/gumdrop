package gumdrop.common;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

  public static String toTitleCase(Enum<?> e) {
    String s = e.toString();
    ByteIterator it = new ByteIterator(s);
    StringBuilder out = new StringBuilder();
    boolean capitalizeNext = true;
    while (!it.done()) {
      char bumped = it.bumpChar();
      char c;
      if (bumped == '_') {
        c = ' ';
        capitalizeNext = true;
      } else {
        if (capitalizeNext) {
          c = bumped;
          capitalizeNext = false;
        } else {
          c = Character.toLowerCase(bumped);
        }
      }
      out.append(c);
    }
    return out.toString();
  }

  public static Map<String, String> parseQueryString(String q) {
    Map<String, String> out = new HashMap<>();
    if (!q.isEmpty()) {
      String[] pairs = q.split("&");
      for (String pair : pairs) {
        int idx = pair.indexOf('=');
        String key = pair.substring(0, idx);
        String value = pair.substring(idx + 1, pair.length());
        out.put(key, value);
      }
    }
    return out;
  }

}
