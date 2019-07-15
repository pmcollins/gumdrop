package gumdrop.json.v2;

public class JsonReader<T> {

  private final Node<T> root;

  public JsonReader(Node<T> root) {
    this.root = root;
  }

  public T read(String json) {
    JsonDelegate d = new StandardJsonDelegate(root);
    new JsonParser(d, json).readValue();
    return root.getValue();
  }

}
