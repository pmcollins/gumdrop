package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;

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
