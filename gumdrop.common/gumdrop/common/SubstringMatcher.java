package gumdrop.common;

/**
 * SubstringMatcher wraps Delimiter which wraps ByteIterator which wraps ByteBuilder.
 *
 * Fast forwards the passed-in iterator to the match point if it exists.
 */
public class SubstringMatcher implements Matcher {

  private byte[] subArray;
  private final Delimiter delim;

  public SubstringMatcher(char stopChar) {
    this(String.valueOf(stopChar));
  }

  public SubstringMatcher(String s) {
    delim = new Delimiter(s);
  }

  @Override
  public boolean match(ByteIterator it) {
    while (!it.done()) {
      if (delim.match(it)) {
        subArray = it.subArray();
        return true;
      }
      if (it.remaining() < delim.length()) {
        return false;
      }
      it.increment();
    }
    return false;
  }

  public byte[] getSubArray() {
    return subArray;
  }

  public String getSubstring() {
    return new String(subArray);
  }

  public boolean done() {
    return subArray != null;
  }

  @Override
  public void skip(ByteIterator it) {
    delim.skip(it);
  }

  public String getTailString() {
    return null;
  }

}
