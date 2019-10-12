package gumdrop.web.http;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;

import java.util.function.Supplier;

public class HttpFormReader<T> implements IFormReader<T> {

  private final Supplier<Deserializer<T>> nodeConstructor;

  public HttpFormReader(Supplier<Deserializer<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public T read(String q) {
    return deserialize(q);
  }

  private T deserialize(String q) {
    Deserializer<T> deserializer = nodeConstructor.get();
    Chainable attributesNode = deserializer.next();
    String[] pairs = q.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      String key = HttpStringUtil.unescape(pair.substring(0, idx));
      String value = HttpStringUtil.unescape(pair.substring(idx + 1));
      Chainable fieldNode = attributesNode.next(key);
      fieldNode.acceptString(value);
    }
    return deserializer.getValue();
  }

}
