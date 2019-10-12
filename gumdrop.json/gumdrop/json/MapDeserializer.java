package gumdrop.json;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapDeserializer<T> extends NullableDeserializer<Map<String, T>> {

  private final Supplier<Deserializer<T>> constructor;

  public MapDeserializer(Supplier<Deserializer<T>> constructor) {
    this.constructor = constructor;
  }

  @Override
  public Chainable next() {
    Map<String, T> map = new HashMap<>();
    setValue(map);
    return new MapElementDeserializer<>(map, constructor);
  }

}
