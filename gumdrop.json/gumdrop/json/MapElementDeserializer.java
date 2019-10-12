package gumdrop.json;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

class MapElementDeserializer<T> extends Deserializer<Map<String, T>> {

  private final Supplier<Deserializer<T>> constructor;

  MapElementDeserializer(Map<String, T> map, Supplier<Deserializer<T>> constructor) {
    super(map);
    this.constructor = constructor;
  }

  @Override
  public Chainable next(String key) {
    Consumer<T> listener = t -> getValue().put(key, t);
    Deserializer<T> deserializer = constructor.get();
    deserializer.setListener(listener);
    return deserializer;
  }

}
