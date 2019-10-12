package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class IntListDeserializer extends NullableDeserializer<List<Integer>> {

  IntListDeserializer() {
  }

  IntListDeserializer(Consumer<List<Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    List<Integer> list = new ArrayList<>();
    setValue(list);
    return new IntListElementDeserializer(list);
  }

}
