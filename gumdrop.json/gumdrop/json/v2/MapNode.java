package gumdrop.json.v2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MapNode<T> extends NullableNode<Map<String, T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  public MapNode(Function<Consumer<T>, Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    Map<String, T> map = new HashMap<>();
    setValue(map);
    return new MapElementNode<>(map, nodeConstructor);
  }

}
