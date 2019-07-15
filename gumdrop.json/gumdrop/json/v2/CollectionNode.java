package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CollectionNode<T, U> extends NullableNode<T> {

  private final Supplier<T> constructor;
  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Node<U>> nodeConstructor;

  public CollectionNode(Supplier<T> constructor, BiConsumer<T, U> method, Function<Consumer<U>, Node<U>> nodeConstructor) {
    this.constructor = constructor;
    this.method = method;
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    T t = constructor.get();
    setValue(t);
    return new CollectionElementNode<>(t, method, nodeConstructor);
  }

}
