package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class BoundaryParser {

  private final KvAccumulator contentType;
  private final KvAccumulator boundary;

  public BoundaryParser() {
    contentType = new KvAccumulator(": ", "; ");
    boundary = new KvAccumulator("=", "\r\n");
  }

  public String getBoundary(CharIterator it) {
    contentType.match(it);
    contentType.skip(it);
    it.mark();
    boundary.match(it);
    return it.substring();
  }

}
