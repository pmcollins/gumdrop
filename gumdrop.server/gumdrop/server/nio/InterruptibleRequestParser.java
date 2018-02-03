package gumdrop.server.nio;

import gumdrop.common.CharIterator;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InterruptibleRequestParser {

  private final WordAccumulator method = new WordAccumulator(' ');
  private final WordAccumulator path = new WordAccumulator(' ');
  private final WordAccumulator protocol = new WordAccumulator('\r');
  private final Iterator<Accumulator> ptr;

  private Accumulator curr;
  private CharIterator it;

  public InterruptibleRequestParser() {
    List<Accumulator> accumulators = new ArrayList<>();
    accumulators.add(method);
    accumulators.add(path);
    accumulators.add(protocol);
    ptr = accumulators.iterator();
    curr = ptr.next();
  }

  public void parse(ByteBuffer bb) {
    String str = new String(bb.array());
    if (it == null) {
      it = new CharIterator(str);
    } else {
      it.append(str);
    }
    while (curr.match(it) && ptr.hasNext()) {
      curr = ptr.next();
      it.increment(); // step over the end of the delimiter
      it.mark();
    }
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

}
