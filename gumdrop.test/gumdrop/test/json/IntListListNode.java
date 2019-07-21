package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;

import java.util.ArrayList;
import java.util.List;

class IntListListNode extends NullableNode<List<List<Integer>>> {

  @Override
  public Chainable next() {
    List<List<Integer>> list = new ArrayList<>();
    setValue(list);
    return new IntListListElementNode(list);
  }

}
