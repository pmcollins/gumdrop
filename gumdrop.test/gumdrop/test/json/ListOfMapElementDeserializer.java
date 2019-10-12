package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;

import java.util.List;
import java.util.Map;

class ListOfMapElementDeserializer extends Deserializer<List<Map<String, Integer>>> {

  ListOfMapElementDeserializer(List<Map<String, Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new StringIntMapDeserializer(map -> getValue().add(map));
  }

}
