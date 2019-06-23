package gumdrop.json2;

import java.util.function.Supplier;

public class TriBinding<T, U> {

  private final Supplier<BaseCreatorNode<U>> creatorNodeSupplier;
  private final TriConsumer<T, String, U> triConsumer;

  public TriBinding(Supplier<BaseCreatorNode<U>> creatorNodeSupplier, TriConsumer<T, String, U> triConsumer) {
    this.creatorNodeSupplier = creatorNodeSupplier;
    this.triConsumer = triConsumer;
  }

  BuilderNode bind(T t, String key) {
    BaseCreatorNode<U> node = creatorNodeSupplier.get();
    U u = node.get();
    triConsumer.accept(t, key, u);
    return node;
  }

}
