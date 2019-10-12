package gumdrop.json;

public class JsonReader<T> {

  private final Deserializer<T> root;

  public JsonReader(Deserializer<T> root) {
    this.root = root;
  }

  public T read(String json) {
    JsonDelegate d = new StandardJsonDelegate(root);
    new JsonParser(d, json).readValue();
    return root.getValue();
  }

}
