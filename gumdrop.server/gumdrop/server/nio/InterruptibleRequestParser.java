package gumdrop.server.nio;

import gumdrop.common.CharIterator;
import gumdrop.common.Request;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InterruptibleRequestParser implements RequestParser {

  private final WordAccumulator method = new WordAccumulator(' ');
  private final WordAccumulator path = new WordAccumulator(' ');
  private final WordAccumulator protocol = new WordAccumulator("\r\n");
  private final AttributeCollectionAccumulator attributes = new AttributeCollectionAccumulator();
  private final PostProcessor postProcessor = new PostProcessor(this);
  private final Iterator<Accumulator> accumPtr;

  private Accumulator curr;
  private final CharIterator it = new CharIterator();

  public InterruptibleRequestParser() {
    List<Accumulator> accumulators = new ArrayList<>();
    accumulators.add(method);
    accumulators.add(path);
    accumulators.add(protocol);
    accumulators.add(attributes);
    accumulators.add(postProcessor);
    accumPtr = accumulators.iterator();
    curr = accumPtr.next();
  }

  public void parse(ByteBuffer bb) {
    it.append(new String(bb.array()));
    while (true) {
      if (!curr.match(it)) break;
      curr.skip(it);
      if (!accumPtr.hasNext()) {
        curr = null;
        break;
      }
      curr = accumPtr.next();
      it.mark();
    }
  }

  public Map<String, String> getAttrs() {
    return attributes.getMap();
  }

  @Override
  public String getAttr(String s) {
    return attributes.get(s);
  }

  @Override
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
    return curr == null && !accumPtr.hasNext();
  }

  public String getPostString() {
    return postProcessor.getPostString();
  }

}

interface RequestParser {

  String getMethod();

  String getAttr(String s);

}

class PostProcessor implements Accumulator {

  private final RequestParser requestParser;
  private String post;

  PostProcessor(RequestParser requestParser) {
    this.requestParser = requestParser;
  }

  @Override
  public boolean match(CharIterator it) {
    if (requestParser.getMethod().equals("POST") && requestParser.getAttr("Content-Type").equals("application/x-www-form-urlencoded")) {
      int contentLength = Integer.parseInt(requestParser.getAttr("Content-Length"));
      if (it.remaining() == contentLength) {
        post = it.tail();
        return true;
      }
    } else {
      return true;
    }
    return false;
  }

  @Override
  public void skip(CharIterator it) {
  }

  public String getPostString() {
    return post;
  }

}
