package gumdrop.server.nio;

import gumdrop.common.ByteIterator;
import gumdrop.common.Matcher;
import gumdrop.common.Delimiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeCollectionMatcher implements Matcher {

  private final List<AttributeMatcher> accumulators = new ArrayList<>();
  private final Delimiter delimiter = new Delimiter("\r\n");
  private AttributeMatcher curr;
  private Map<String, String> map;

  @Override
  public boolean match(ByteIterator it) {
    if (curr == null) {
      if (delimiter.match(it)) return true;
      curr = new AttributeMatcher();
    }
    while (true) {
      if (curr.match(it)) {
        curr.skip(it);
        it.mark();
        accumulators.add(curr);
        if (delimiter.match(it)) return true;
        if (it.remaining() < delimiter.length()) {
          // We just matched. At this point, we're pointing to the beginning of a new line, yet that line is mostly empty.
          // Don't leave an AttributeAccumulator which will preempt a possible delimiter match on the next round.
          curr = null;
          return false;
        } else {
          curr = new AttributeMatcher();
        }
      } else {
        return false;
      }
    }
  }

  @Override
  public void skip(ByteIterator it) {
    delimiter.skip(it);
  }

  public Map<String, String> getMap() {
    if (map == null) {
      map = new HashMap<>();
      for (AttributeMatcher accumulator : accumulators) {
        String key = accumulator.getKey();
        String value = accumulator.getValue();
        map.put(key, value);
      }
    }
    return map;
  }

  public String get(String key) {
    return getMap().get(key);
  }

}
