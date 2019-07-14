package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.IntNode;
import gumdrop.json.v2.Node;

import java.util.List;
import java.util.function.Consumer;

class IntListElementNode extends Node<List<Integer>> implements Consumer<Integer> {

  IntListElementNode(List<Integer> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntNode(this);
  }

  @Override
  public void accept(Integer integer) {
    getValue().add(integer);
  }

}
