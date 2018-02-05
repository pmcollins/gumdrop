package gumdrop.web;

import gumdrop.common.CharIterator;

public class WordAccumulator implements Accumulator {

  private String val;
  private final Delimiter delim;

  public WordAccumulator(char stopChar) {
    this(String.valueOf(stopChar));
  }

  public WordAccumulator(String s) {
    delim = new Delimiter(s);
  }

  @Override
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

  public boolean done() {
    return val != null;
  }

  @Override
  public void skip(CharIterator it) {
    delim.skip(it);
  }

}
