package gumdrop.server.nio;

import gumdrop.common.CharIterator;

interface Accumulator {

  boolean match(CharIterator it);

  void skip(CharIterator it);

}
