package gumdrop.json;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

class MapElementNode<T> extends Node<Map<String, T>> {

  private final Supplier<Node<T>> constructor;

  MapElementNode(Map<String, T> map, Supplier<Node<T>> constructor) {
    super(map);
    this.constructor = constructor;
  }

  @Override
  public Chainable next(String key) {
    Consumer<T> listener = t -> getValue().put(key, t);
    Node<T> node = constructor.get();
    node.setListener(listener);
    return node;
  }

}
