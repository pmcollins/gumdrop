package gumdrop.server.nio;

import gumdrop.common.CharIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeCollectionAccumulator implements Accumulator {

  private final List<AttributeAccumulator> accumulators = new ArrayList<>();
  private AttributeAccumulator curr;
  private final FwdDelimiter delimiter = new FwdDelimiter("\r\n");

  @Override
  public boolean match(CharIterator it) {
    if (curr == null) {
      if (delimiter.match(it)) return true;
      curr = new AttributeAccumulator();
    }
    while (true) {
      if (curr.match(it)) {
        it.increment(); // \r
        it.increment(); // \n
        it.mark();
        accumulators.add(curr);
        if (delimiter.match(it)) return true;
        if (it.remaining() < delimiter.length()) {
          // We just matched. At this point, we're pointing to the beginning of a new line, yet that line is mostly empty.
          // Don't leave an AttributeAccumulator which will preempt a possible delimiter match on the next round.
          curr = null;
          return false;
        } else {
          curr = new AttributeAccumulator();
        }
      } else {
        return false;
      }
    }
  }

  public Map<String, String> getMap() {
    Map<String, String> out = new HashMap<>();
    for (AttributeAccumulator accumulator : accumulators) {
      String key = accumulator.getKey();
      String value = accumulator.getValue();
      out.put(key, value);
    }
    return out;
  }

}