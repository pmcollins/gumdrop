package gumdrop.common;

import gumdrop.common.builder.ByteIterator;

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

}
