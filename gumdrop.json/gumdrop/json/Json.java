package gumdrop.json;

public final class Json {

  private Json() {}

  public static <T> T deserialize(Deserializer<T> deserializer, String json) {
    JsonDelegate delegate = new StandardJsonDelegate(deserializer);
    new JsonParser(delegate, json).readValue();
    return deserializer.getValue();
  }

}
