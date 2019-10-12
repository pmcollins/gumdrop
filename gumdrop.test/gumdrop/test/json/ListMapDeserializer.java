package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListMapDeserializer extends NullableDeserializer<Map<String, List<Integer>>> {

  @Override
  public Chainable next() {
    Map<String, List<Integer>> map = new HashMap<>();
    setValue(map);
    return new ListMapElementDeserializer(map);
  }

}
