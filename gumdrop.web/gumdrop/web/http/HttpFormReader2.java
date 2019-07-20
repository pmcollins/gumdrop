package gumdrop.web.http;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;
import gumdrop.web.controller.ReadResult;

import java.util.function.Supplier;

public class HttpFormReader2<T> implements IFormReader<T> {

  private final Supplier<Node<T>> nodeConstructor;

  public HttpFormReader2(Supplier<Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public ReadResult<T> read(String q) {
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
