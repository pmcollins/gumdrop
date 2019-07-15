package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class CollectionElementNode<T, U> extends Node<T> {

  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Node<U>> nodeConstructor;

  CollectionElementNode(T t, BiConsumer<T, U> method, Function<Consumer<U>, Node<U>> nodeConstructor) {
    super(t);
    this.method = method;
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    Consumer<U> callback = u -> {
      T t = getValue();
      method.accept(t, u);
    };
    return nodeConstructor.apply(callback);
  }

}
