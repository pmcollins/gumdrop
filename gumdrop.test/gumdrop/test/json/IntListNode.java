package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class IntListNode extends NullableNode<List<Integer>> {

  IntListNode() {
  }

  IntListNode(Consumer<List<Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    List<Integer> list = new ArrayList<>();
    setValue(list);
    return new IntListElementNode(list);
  }

}
