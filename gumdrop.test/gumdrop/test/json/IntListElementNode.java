package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntNode;
import gumdrop.json.Node;

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
