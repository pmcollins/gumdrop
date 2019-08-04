package gumdrop.web.http;

import gumdrop.json.Chainable;
import gumdrop.json.Node;

import java.util.function.Supplier;

public class HttpFormReader<T> implements IFormReader<T> {

  private final Supplier<Node<T>> nodeConstructor;

  public HttpFormReader(Supplier<Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public T read(String q) {
    return deserialize(q);
  }

  private T deserialize(String q) {
    Node<T> node = nodeConstructor.get();
    Chainable attributesNode = node.next();
    String[] pairs = q.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      String key = HttpStringUtil.unescape(pair.substring(0, idx));
      String value = HttpStringUtil.unescape(pair.substring(idx + 1));
      Chainable fieldNode = attributesNode.next(key);
      fieldNode.acceptString(value);
    }
    return node.getValue();
  }

}
