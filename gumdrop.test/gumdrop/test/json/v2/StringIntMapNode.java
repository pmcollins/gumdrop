package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class StringIntMapNode extends NullableNode<Map<String, Integer>> {

  StringIntMapNode() {
  }

  StringIntMapNode(Consumer<Map<String, Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    HashMap<String, Integer> value = new HashMap<>();
    setValue(value);
    return new StringIntMapElementNode(value);
  }

}
