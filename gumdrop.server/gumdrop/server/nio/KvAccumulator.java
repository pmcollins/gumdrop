package gumdrop.server.nio;

import gumdrop.common.CharIterator;

class KvAccumulator implements Accumulator {

  private final WordAccumulator w1;
  private final WordAccumulator w2;

  KvAccumulator(String delim1, String delim2) {
    w1 = new WordAccumulator(delim1);
    w2 = new WordAccumulator(delim2);
  }

  @Override
  public boolean match(CharIterator it) {
    w1.match(it);
    w1.skip(it);
    it.mark();
    w2.match(it);
    return true;
  }

  @Override
  public void skip(CharIterator it) {
    w2.skip(it);
  }

  String getKey() {
    return w1.getVal();
  }

  String getValue() {
    return w2.getVal();
  }

}
