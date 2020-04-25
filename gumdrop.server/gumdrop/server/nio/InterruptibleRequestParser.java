package gumdrop.server.nio;

import gumdrop.common.ByteIterator;
import gumdrop.common.StringUtil;
import gumdrop.common.http.HttpMethod;
import gumdrop.common.http.HttpRequest;
import gumdrop.common.Matcher;
import gumdrop.common.SubstringMatcher;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InterruptibleRequestParser implements RequestParser {

  private final SubstringMatcher method = new SubstringMatcher(' ');
  private final SubstringMatcher path = new SubstringMatcher(' ');
  private final SubstringMatcher protocol = new SubstringMatcher("\r\n");
  private final AttributeCollectionMatcher attributes = new AttributeCollectionMatcher();
  private final PostProcessor postProcessor = new PostProcessor(this);
  private final Iterator<Matcher> accumPtr;

  private Matcher curr;
  private final ByteIterator it = new ByteIterator();

  public InterruptibleRequestParser() {
    List<Matcher> matchers = new ArrayList<>();
    matchers.add(method);
    matchers.add(path);
    matchers.add(protocol);
    matchers.add(attributes);
    matchers.add(postProcessor);
    accumPtr = matchers.iterator();
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

  private byte[] getPostBytes() {
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
      Map<String, String> parameterMap = StringUtil.parseQueryString(request.getPostString());
      request.setParameterMap(parameterMap);
    }
    return request;
  }

}
