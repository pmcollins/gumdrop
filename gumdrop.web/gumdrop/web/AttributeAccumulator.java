package gumdrop.web;

import gumdrop.common.CharIterator;

public class AttributeAccumulator implements Accumulator {

  private final WordAccumulator key = new WordAccumulator(": ");
  private final WordAccumulator value = new WordAccumulator("\r\n");

  @Override
  public boolean match(CharIterator it) {
    if (key.done()) {
      return value.match(it);
    } else {
      if (key.match(it)) {
        key.skip(it);
        it.mark();
        return value.match(it);
      }
      return false;
    }
  }

  @Override
  public void skip(CharIterator it) {
    // no reason why this can't be done in match() and no-op here
    value.skip(it);
  }

  public String getKey() {
    return key.getSubstring();
  }

  public String getValue() {
    return value.getSubstring();
  }

}
