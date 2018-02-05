package gumdrop.web;

import gumdrop.common.CharIterator;

public interface Accumulator {

  boolean match(CharIterator it);

  void skip(CharIterator it);

}
