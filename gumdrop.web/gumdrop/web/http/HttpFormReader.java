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
  public FormReadResult<T> read(String q) {
    Node<T> node = nodeConstructor.get();
    Chainable attributesNode = node.next();
    FormReadResult<T> out = new FormReadResult<>();
    String[] pairs = q.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      String key = HttpStringUtil.unescape(pair.substring(0, idx));
      String value = HttpStringUtil.unescape(pair.substring(idx + 1));
      Chainable fieldNode = attributesNode.next(key);
      fieldNode.acceptString(value);
    }
    out.setT(node.getValue());
    return out;
  }

}
