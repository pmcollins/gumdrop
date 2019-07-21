package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Node;

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
