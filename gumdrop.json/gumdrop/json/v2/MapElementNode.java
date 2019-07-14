package gumdrop.json.v2;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

class MapElementNode<T> extends Node<Map<String, T>> {

  private final Function<Consumer<T>, Node<T>> constructor;

  MapElementNode(Map<String, T> map, Function<Consumer<T>, Node<T>> constructor) {
    super(map);
    this.constructor = constructor;
  }

  @Override
  public Chainable next(String key) {
    return constructor.apply(t -> getValue().put(key, t));
  }

}
