package gumdrop.json.v2;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

class ListElementNode<T> extends Node<List<T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  ListElementNode(List<T> list, Function<Consumer<T>, Node<T>> nodeConstructor) {
    super(list);
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    return nodeConstructor.apply(list -> {
      List<T> value = getValue();
      value.add(list);
    });
  }

}
