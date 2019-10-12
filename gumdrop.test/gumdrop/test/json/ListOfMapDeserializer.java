package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ListOfMapDeserializer extends Deserializer<List<Map<String, Integer>>> {

  @Override
  public Chainable next() {
    ArrayList<Map<String, Integer>> list = new ArrayList<>();
    setValue(list);
    return new ListOfMapElementDeserializer(list);
  }

}
