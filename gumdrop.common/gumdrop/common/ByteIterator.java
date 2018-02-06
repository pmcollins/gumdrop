package gumdrop.common;

public class ByteIterator {

  private final ByteBuilder bb;
  private int i;
  private int mark;

  public ByteIterator() {
    bb = new ByteBuilder();
  }

  public ByteIterator(String str) {
    this(str.getBytes());
  }

  public ByteIterator(byte[] bytes) {
    bb = new ByteBuilder();
    bb.append(bytes);
  }

  public void append(String string) {
    append(string.getBytes());
  }

  public void append(byte[] bytes) {
    bb.append(bytes);
  }

  public char currentChar() {
    return bb.charAt(i);
  }

  public byte current() {
    return bb.byteAt(i);
  }

  public void mark() {
    mark = i;
  }

  public String substring() {
    return bb.substring(mark, i);
  }

  public byte[] subArray() {
    return bb.subarray(mark, i);
  }

  /**
   * With arg 0 behaves like substring(). Otherwise shifts the effective position (i) by the offset amount.
   * If substring() returns "abc", passing in an offset of -1 will return "ab".
   */
  public String substring(int offset) {
    return bb.substring(mark, Math.max(mark, i + offset));
  }

  public String tailString() {
    return new String(tail());
  }

  public byte[] tail() {
    return bb.subarray(i, bb.length());
  }

  public int remaining() {
    return bb.length() - i;
  }

  public void increment() {
    increment(1);
  }

  public void increment(int n) {
    int next = i + n;
    if (next <= bb.length()) {
      i = next;
    } else {
      throw new IllegalStateException("already at end of string");
    }
  }

  public char bumpChar() {
    return (char) bump();
  }

  public byte bump() {
    byte out = current();
    increment();
    return out;
  }

  public char nextChar() {
    return (char) next();
  }

  public byte next() {
    increment();
    return current();
  }

  public boolean done() {
    return i == bb.length();
  }

  public int length() {
    return bb.length();
  }

  public void reset() {
    i = 0;
  }

  public void positionLast() {
    i = bb.length() - 1;
  }

  public int position() {
    return i;
  }

  public void position(int i) {
    this.i = i;
  }

  public void decrement() {
    if (i == 0) {
      throw new IllegalStateException("already at beginning of array");
    } else {
      --i;
    }
  }

}
