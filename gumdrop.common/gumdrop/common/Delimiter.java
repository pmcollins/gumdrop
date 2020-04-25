package gumdrop.common;

/**
 * Delimeter wraps a ByteIterator which wraps a ByteBuilder
 */
public class Delimiter implements Matcher {

  private final ByteIterator delim;

  public Delimiter(String s) {
    delim = new ByteIterator(s);
  }

  /**
   * Returns true only if the current position of the passed-in iterator matches the delimiter.
   */
  @Override
  public boolean match(ByteIterator it) {
    int itPos = it.position();
    boolean out = false;
    while (it.current() == delim.current()) {
      it.increment();
      delim.increment();
      if (delim.done()) {
        out = true;
        break;
      }
    }
    // TODO if delim incremented {
    reset();
    it.position(itPos);
    // TODO }
    return out;
  }

  @Override
  public void skip(ByteIterator it) {
    it.increment(delim.length());
  }

  public int length() {
    return delim.length();
  }

  private void reset() {
    delim.position(0);
  }

}
