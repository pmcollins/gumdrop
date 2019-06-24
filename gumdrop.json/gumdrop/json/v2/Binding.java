package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Binding<T, U> {

  private final Supplier<BaseCreatorNode<U>> creatorNodeSupplier;
  private final BiConsumer<T, U> setter;

  public Binding(Supplier<BaseCreatorNode<U>> creatorNodeSupplier, BiConsumer<T, U> setter) {
    this.creatorNodeSupplier = creatorNodeSupplier;
    this.setter = setter;
  }

  CreatorNode bind(T t) {
    BaseCreatorNode<U> creatorNode = creatorNodeSupplier.get();
    U u = creatorNode.get();
    setter.accept(t, u);
    return creatorNode;
  }

}
