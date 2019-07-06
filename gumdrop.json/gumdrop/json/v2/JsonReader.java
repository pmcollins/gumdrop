package gumdrop.json.v2;

public class JsonReader<T> {

  private final Node<T> node;

  public JsonReader(Node<T> node) {
    this.node = node;
  }

  public T read(String json) {
    JsonDelegate d = new LoggingJsonDelegate(node);
    JsonParser p = new JsonParser(d, json);
    p.readValue();
    return node.instance();
  }

}
