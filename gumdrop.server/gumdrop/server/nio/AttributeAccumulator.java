package gumdrop.server.nio;

import gumdrop.common.builder.ByteIterator;
import gumdrop.web.Accumulator;
import gumdrop.web.WordAccumulator;

public class AttributeAccumulator implements Accumulator {

  private final WordAccumulator key = new WordAccumulator(": ");
  private final WordAccumulator value = new WordAccumulator("\r\n");

  @Override
  public boolean match(ByteIterator it) {
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
  public void skip(ByteIterator it) {
    // could be done in match() and no-op here
    value.skip(it);
  }

  public String getKey() {
    return key.getSubstring();
  }

  public String getValue() {
    return value.getSubstring();
  }

}
