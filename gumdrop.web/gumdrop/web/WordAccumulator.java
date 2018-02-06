package gumdrop.web;

import gumdrop.common.ByteIterator;

public class WordAccumulator implements Accumulator {

  private byte[] subArray;
  private final Delimiter delim;

  public WordAccumulator(char stopChar) {
    this(String.valueOf(stopChar));
  }

  public WordAccumulator(String s) {
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

}
