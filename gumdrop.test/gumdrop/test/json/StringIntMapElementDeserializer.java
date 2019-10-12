package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntDeserializer;
import gumdrop.json.Deserializer;

import java.util.Map;

class StringIntMapElementDeserializer extends Deserializer<Map<String, Integer>> {

  StringIntMapElementDeserializer(Map<String, Integer> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntDeserializer(i -> getValue().put(key, i));
  }

}
