package gumdrop.json;

public class CharIterator {

  /**
   * From Character Iterator:
   * Constant that is returned when the iterator has reached either the end
   * or the beginning of the text. The value is '\\uFFFF', the "not a
   * character" value which should not occur in any valid Unicode string.
   */
  public static final char DONE = '\uFFFF';

  private final String s;
  private int i;
  private int mark;

  public CharIterator(String s) {
    this.s = s;
  }

  public char current() {
    return i == s.length() ? DONE : s.charAt(i);
  }

  public void mark() {
    mark = i;
  }

  public String substring() {
    return s.substring(mark, i);
  }

  public void increment() {
    if (i < s.length()) {
      ++i;
    } else {
      throw new IllegalStateException("already at end of string");
    }
  }

  public char bump() {
    char out = current();
    increment();
    return out;
  }

  public char next() {
    increment();
    return current();
  }

}
