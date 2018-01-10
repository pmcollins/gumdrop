package gumdrop.common;

public class CharIterator {

  /**
   * From CharacterIterator:
   * Constant that is returned when the iterator has reached either the end
   * or the beginning of the text. The value is '\\uFFFF', the "not a
   * character" value which should not occur in any valid Unicode string.
   */
  public static final char DONE = '\uFFFF';

  private final StringBuilder sb;
  private int i;
  private int mark;

  public CharIterator(String str) {
    sb = new StringBuilder(str);
  }

  public void append(String string) {
    sb.append(string);
  }

  public char current() {
    return i == sb.length() ? DONE : sb.charAt(i);
  }

  public void mark() {
    mark = i;
  }

  public String substring() {
    return sb.substring(mark, i);
  }

  public String substring(int offset) {
    return sb.substring(mark, Math.max(mark, i + offset));
  }

  public void increment() {
    if (i < sb.length()) {
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

  public boolean done() {
    return current() == CharIterator.DONE;
  }

}