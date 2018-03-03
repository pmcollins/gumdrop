package gumdrop.web;

import gumdrop.common.ByteIterator;

public class KvAccumulator implements Accumulator {

  private final WordAccumulator key;
  private final WordAccumulator value;

  KvAccumulator(String delim1, String delim2) {
    key = new WordAccumulator(delim1);
    value = new WordAccumulator(delim2);
  }

  @Override
  public boolean match(ByteIterator it) {
    key.match(it);
    key.skip(it);
    it.mark();
    value.match(it);
    return true;
  }

  @Override
  public void skip(ByteIterator it) {
    value.skip(it);
  }

  String getKey() {
    return key.getSubstring();
  }

  String getValue() {
    return value.getSubstring();
  }

}
