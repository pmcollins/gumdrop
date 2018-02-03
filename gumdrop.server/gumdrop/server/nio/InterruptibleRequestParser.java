package gumdrop.server.nio;

import gumdrop.common.CharIterator;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InterruptibleRequestParser {

  private final WordAccumulator method = new WordAccumulator(' ');
  private final WordAccumulator path = new WordAccumulator(' ');
  private final WordAccumulator protocol = new WordAccumulator("\r\n");
  private final AttributeCollectionAccumulator ac = new AttributeCollectionAccumulator();
  private final Iterator<Accumulator> accumPtr;

  private Accumulator curr;
  private final CharIterator it = new CharIterator();

  public InterruptibleRequestParser() {
    List<Accumulator> accumulators = new ArrayList<>();
    accumulators.add(method);
    accumulators.add(path);
    accumulators.add(protocol);
    accumulators.add(ac);
    accumPtr = accumulators.iterator();
    curr = accumPtr.next();
  }

  public void parse(ByteBuffer bb) {
    it.append(new String(bb.array()));
    while (true) {
      if (!curr.match(it)) break;
      curr.skip(it);
      if (!accumPtr.hasNext()) break;
      curr = accumPtr.next();
      it.mark();
    }
  }

  private void handlePost() {
    if (getAttrs().get("Content-Type").equals("application/x-www-form-urlencoded")) {
      int contentLength = Integer.parseInt(getAttrs().get("Content-Length"));
      String tail = it.tail();
      System.out.println("tail = [" + tail + "]");
    }
  }

  public Map<String, String> getAttrs() {
    return ac.getMap();
  }

  public String getMethod() {
    return method.getVal();
  }

  public String getPath() {
    return path.getVal();
  }

  public String getProtocol() {
    return protocol.getVal();
  }

  public boolean done() {
    return false;
  }

}
