package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class StringIntMapDeserializer extends NullableDeserializer<Map<String, Integer>> {

  StringIntMapDeserializer() {
  }

  StringIntMapDeserializer(Consumer<Map<String, Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    HashMap<String, Integer> value = new HashMap<>();
    setValue(value);
    return new StringIntMapElementDeserializer(value);
  }

}
