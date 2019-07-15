package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class FieldBinding<T, U> {

  private final String name;
  private final BiConsumer<T, U> setter;
  private final Function<Consumer<U>, Node<U>> nodeConstructor;

  public FieldBinding(String name, BiConsumer<T, U> setter, Function<Consumer<U>, Node<U>> nodeConstructor) {
    this.name = name;
    this.setter = setter;
    this.nodeConstructor = nodeConstructor;
  }

  String getName() {
    return name;
  }

  Node<U> buildNode(T t) {
    return nodeConstructor.apply(value -> setter.accept(t, value));
  }

}
