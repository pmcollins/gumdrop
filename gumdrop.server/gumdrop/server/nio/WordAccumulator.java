package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class WordAccumulator implements Accumulator {

  private String val;
  private final FwdDelimiter delim;

  WordAccumulator(char stopChar) {
    this(String.valueOf(stopChar));
  }

  public WordAccumulator(String s) {
    delim = new FwdDelimiter(s);
  }

  public boolean match(CharIterator it) {
    while (!it.done()) {
      if (delim.match(it)) {
        val = it.substring();
        return true;
      }
      if (it.remaining() < delim.length()) {
        return false;
      }
      it.increment();
    }
    return false;
  }

  public String getVal() {
    return val;
  }

  boolean done() {
    return val != null;
  }

}
