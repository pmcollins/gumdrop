package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;

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
