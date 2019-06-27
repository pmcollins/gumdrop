package gumdrop.json.v2;

import java.util.function.Supplier;

public class TriBinding<T, U> {

  private final Supplier<SupplierNode<U>> creatorNodeSupplier;
  private final TriConsumer<T, String, U> triConsumer;

  public TriBinding(Supplier<SupplierNode<U>> creatorNodeSupplier, TriConsumer<T, String, U> triConsumer) {
    this.creatorNodeSupplier = creatorNodeSupplier;
    this.triConsumer = triConsumer;
  }

  Node bind(T t, String key) {
    SupplierNode<U> node = creatorNodeSupplier.get();
    U u = node.get();
    triConsumer.accept(t, key, u);
    return node;
  }

}
