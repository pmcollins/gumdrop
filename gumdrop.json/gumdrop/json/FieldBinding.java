package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldBinding<T, U> {

  private final String name;
  private final BiConsumer<T, U> setter;
  private final Supplier<Node<U>> nodeConstructor;

  public FieldBinding(String name, BiConsumer<T, U> setter, Supplier<Node<U>> nodeConstructor) {
    this.name = name;
    this.setter = setter;
    this.nodeConstructor = nodeConstructor;
  }

  String getName() {
    return name;
  }

  Node<U> buildNode(T t) {
    Consumer<U> listener = value -> setter.accept(t, value);
    Node<U> node = nodeConstructor.get();
    node.setListener(listener);
    return node;
  }

}
