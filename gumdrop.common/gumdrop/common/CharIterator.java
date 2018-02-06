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

  public CharIterator() {
    sb = new StringBuilder();
  }

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

  /**
   * With arg 0 behaves like substring(). Otherwise shifts the effective position (i) by the offset amount.
   * If substring() returns "abc", passing in an offset of -1 will return "ab".
   */
  public String substring(int offset) {
    return sb.substring(mark, Math.max(mark, i + offset));
  }

  public String tail() {
    return sb.substring(i, sb.length());
  }

  public int remaining() {
    return sb.length() - i;
  }

  public void increment() {
    increment(1);
  }

  public void increment(int n) {
    int next = i + n;
    if (next <= sb.length()) {
      i = next;
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

  public int length() {
    return sb.length();
  }

  public void reset() {
    i = 0;
  }

  public void positionLast() {
    i = sb.length() - 1;
  }

  public int position() {
    return i;
  }

  public void position(int i) {
    this.i = i;
  }

  public void decrement() {
    if (i == 0) {
      throw new IllegalStateException("already at beginning of string");
    } else {
      --i;
    }
  }

}
