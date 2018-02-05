package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class MultiParser {

  private final Accumulator[] q;
  private String val;

  public MultiParser() {
    Delimiter crlf = new Delimiter("\r\n");
    KvAccumulator cd = new KvAccumulator(": ", ";");
    KvAccumulator name = new KvAccumulator("=", ";");
    KvAccumulator fn = new KvAccumulator("=", "\r\n");
    KvAccumulator type = new KvAccumulator(": ", "\r\n");
    q = new Accumulator[] {crlf, cd, name, fn, type, crlf};
  }

  public void parse(String delimStr, CharIterator it) {
    Delimiter startBoundary = new Delimiter(delimStr);
    startBoundary.match(it);
    startBoundary.skip(it);

    for (Accumulator acc : q) {
      acc.match(it);
      acc.skip(it);
      it.mark();
    }

    WordAccumulator content = new WordAccumulator("\r\n" + delimStr + "--");
    content.match(it);
    val = content.getVal();
  }

  public String getVal() {
    return val;
  }

}
