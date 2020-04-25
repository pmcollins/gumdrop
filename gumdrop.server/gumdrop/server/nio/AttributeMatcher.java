package gumdrop.server.nio;

import gumdrop.common.ByteIterator;
import gumdrop.common.Matcher;
import gumdrop.common.SubstringMatcher;

public class AttributeMatcher implements Matcher {

  private final SubstringMatcher key = new SubstringMatcher(": ");
  private final SubstringMatcher value = new SubstringMatcher("\r\n");

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
