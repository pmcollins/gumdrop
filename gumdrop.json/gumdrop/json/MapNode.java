package gumdrop.json;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapNode<T> extends NullableNode<Map<String, T>> {

  private final Supplier<Node<T>> constructor;

  public MapNode(Supplier<Node<T>> constructor) {
    this.constructor = constructor;
  }

  @Override
  public Chainable next() {
    Map<String, T> map = new HashMap<>();
    setValue(map);
    return new MapElementNode<>(map, constructor);
  }

}
