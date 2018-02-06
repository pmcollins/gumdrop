package gumdrop.web;

import gumdrop.common.ByteIterator;

public interface Accumulator {

  boolean match(ByteIterator it);

  void skip(ByteIterator it);

}
