package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;

import java.util.List;
import java.util.Map;

class ListMapElementDeserializer extends Deserializer<Map<String, List<Integer>>> {

  ListMapElementDeserializer(Map<String, List<Integer>> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntListDeserializer(list -> getValue().put(key, list));
  }

}
