package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;

import java.util.List;

class IntListListElementNode extends Node<List<List<Integer>>> {

  IntListListElementNode(List<List<Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntListNode(list -> {
      List<List<Integer>> value = getValue();
      value.add(list);
    });
  }

}
