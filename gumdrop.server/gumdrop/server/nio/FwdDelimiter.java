package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class FwdDelimiter implements Accumulator {

  private final CharIterator delim;

  public FwdDelimiter(String s) {
    delim = new CharIterator(s);
  }

  @Override
  public boolean match(CharIterator it) {
    int itPos = it.position();
    boolean out = false;
    while (it.current() == delim.current()) {
      it.increment();
      delim.increment();
      if (delim.done()) {
        out = true;
        break;
      }
    }
    reset();
    it.position(itPos);
    return out;
  }

  public boolean matching() {
    return delim.position() > 0;
  }

  public int length() {
    return delim.length();
  }

  private void reset() {
    delim.position(0);
  }

}