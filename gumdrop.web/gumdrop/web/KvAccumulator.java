package gumdrop.web;

import gumdrop.common.ByteIterator;

public class KvAccumulator implements Accumulator {

  private final WordAccumulator w1;
  private final WordAccumulator w2;

  KvAccumulator(String delim1, String delim2) {
    w1 = new WordAccumulator(delim1);
    w2 = new WordAccumulator(delim2);
  }

  @Override
  public boolean match(ByteIterator it) {
    w1.match(it);
    w1.skip(it);
    it.mark();
    w2.match(it);
    return true;
  }

  @Override
  public void skip(ByteIterator it) {
    w2.skip(it);
  }

  String getKey() {
    return w1.getSubstring();
  }

  String getValue() {
    return w2.getSubstring();
  }

}
