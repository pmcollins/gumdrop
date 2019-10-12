package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;

import java.util.ArrayList;
import java.util.List;

class IntListListDeserializer extends NullableDeserializer<List<List<Integer>>> {

  @Override
  public Chainable next() {
    List<List<Integer>> list = new ArrayList<>();
    setValue(list);
    return new IntListListElementDeserializer(list);
  }

}
