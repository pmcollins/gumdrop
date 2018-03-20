package gumdrop.web;

import gumdrop.common.builder.ByteIterator;

public interface Accumulator {

  boolean match(ByteIterator it);

  void skip(ByteIterator it);

}
