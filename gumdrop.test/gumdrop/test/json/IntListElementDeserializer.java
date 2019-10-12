package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntDeserializer;
import gumdrop.json.Deserializer;

import java.util.List;
import java.util.function.Consumer;

class IntListElementDeserializer extends Deserializer<List<Integer>> implements Consumer<Integer> {

  IntListElementDeserializer(List<Integer> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntDeserializer(this);
  }

  @Override
  public void accept(Integer integer) {
    getValue().add(integer);
  }

}
