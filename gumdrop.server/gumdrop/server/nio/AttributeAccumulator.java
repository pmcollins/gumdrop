package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class AttributeAccumulator implements Accumulator {

  private final WordAccumulator key = new WordAccumulator(": ");
  private final WordAccumulator value = new WordAccumulator("\r\n");

  @Override
  public boolean match(CharIterator it) {
    if (key.done()) {
      return value.match(it);
    } else {
      boolean keyMatch = key.match(it);
      //noinspection SimplifiableIfStatement
      if (keyMatch) {
        it.increment(); // :
        it.increment(); // ' '
        it.mark();
        return value.match(it);
      }
      return false;
    }
  }

  public String getKey() {
    return key.getVal();
  }

  public String getValue() {
    return value.getVal();
  }

}
