package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Binding<T, U> implements Function<T, Node> {

  private final BiConsumer<T, U> setter;
  private final Supplier<SupplierNode<U>> creatorNodeSupplier;

  public Binding(BiConsumer<T, U> setter, Supplier<SupplierNode<U>> creatorNodeSupplier) {
    this.setter = setter;
    this.creatorNodeSupplier = creatorNodeSupplier;
  }

  @Override
  public Node apply(T t) {
    SupplierNode<U> creatorNode = creatorNodeSupplier.get();
    U u = creatorNode.get();
    setter.accept(t, u);
    return creatorNode;
  }

}
