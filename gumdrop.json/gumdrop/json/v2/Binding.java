package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Binding<T, U> implements Function<T, Chainable> {

  private final BiConsumer<T, U> setter;
  private final Supplier<Node<U>> nodeSupplier;

  public Binding(BiConsumer<T, U> setter, Supplier<Node<U>> nodeSupplier) {
    this.setter = setter;
    this.nodeSupplier = nodeSupplier;
  }

  @Override
  public Chainable apply(T t) {
    Node<U> node = nodeSupplier.get();
    U u = node.instance();
    setter.accept(t, u);
    return node;
  }

}
