package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class CollectionElementNode<T, U> extends Node<T> {

  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Node<U>> constructorWithCallback;
  private final Supplier<Node<U>> constructor;

  CollectionElementNode(T t, BiConsumer<T, U> method, Function<Consumer<U>, Node<U>> constructorWithCallback) {
    super(t);
    this.method = method;
    this.constructorWithCallback = constructorWithCallback;
    constructor = null;
  }

  CollectionElementNode(T t, BiConsumer<T, U> method, Supplier<Node<U>> constructor) {
    super(t);
    this.method = method;
    this.constructor = constructor;
    constructorWithCallback = null;
  }

  @Override
  public Chainable next() {
    Consumer<U> listener = u -> {
      T t = getValue();
      method.accept(t, u);
    };
    if (constructorWithCallback != null) {
      return constructorWithCallback.apply(listener);
    } else if (constructor != null) {
      Node<U> node = constructor.get();
      node.setListener(listener);
      return node;
    }
    return null;
  }

}
