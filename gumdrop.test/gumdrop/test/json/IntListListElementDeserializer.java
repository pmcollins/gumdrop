package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;

import java.util.List;

class IntListListElementDeserializer extends Deserializer<List<List<Integer>>> {

  IntListListElementDeserializer(List<List<Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntListDeserializer(list -> {
      List<List<Integer>> value = getValue();
      value.add(list);
    });
  }

}
