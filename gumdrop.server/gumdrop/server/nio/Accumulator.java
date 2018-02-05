package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public interface Accumulator {

  boolean match(CharIterator it);

  void skip(CharIterator it);

}
