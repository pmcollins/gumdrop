package gumdrop.server.nio;

import gumdrop.common.ByteIterator;
import gumdrop.common.HttpMethod;
import gumdrop.common.HttpRequest;
import gumdrop.web.Accumulator;
import gumdrop.web.AttributeCollectionAccumulator;
import gumdrop.web.WordAccumulator;

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
  private final ByteIterator it = new ByteIterator();

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
    byte[] a = new byte[bb.remaining()];
    bb.get(a);
    it.append(a);
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

  @Override
  public String getAttr(String s) {
    return attributes.get(s);
  }

  @Override
  public String getMethod() {
    return method.getSubstring();
  }

  public String getPath() {
    return path.getSubstring();
  }

  public String getProtocol() {
    return protocol.getSubstring();
  }

  public boolean done() {
    return curr == null && !accumPtr.hasNext();
  }

  public String getPostString() {
    return postProcessor.getPostString();
  }

  public byte[] getPostBytes() {
    return postProcessor.getPostBytes();
  }

  private boolean isFormPost(HttpRequest request) {
    return request.isPost() && isFormType();
  }

  private boolean isFormType() {
    return getAttr("Content-Type").equals("application/x-www-form-urlencoded");
  }

  public HttpRequest getRequest() {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethod.valueOf(getMethod()));
    request.setPath(getPath());
    request.setProtocol(getProtocol());
    Map<String, String> map = attributes.getMap();
    request.setHeaders(map);
    request.setPost(getPostBytes());
    if (isFormPost(request)) {
      request.writeParameterMap();
    }
    return request;
  }

}
