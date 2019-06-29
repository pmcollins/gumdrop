package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

import java.util.function.Supplier;

public class DictionaryBinding<T, U> {

  private final Supplier<SupplierNode<U>> creatorNodeSupplier;
  private final TriConsumer<T, String, U> putMethod;

  public DictionaryBinding(Supplier<SupplierNode<U>> creatorNodeSupplier, TriConsumer<T, String, U> putMethod) {
    this.creatorNodeSupplier = creatorNodeSupplier;
    this.putMethod = putMethod;
  }

  Node bind(T t, String key) {
    SupplierNode<U> node = creatorNodeSupplier.get();
    U u = node.get();
    putMethod.accept(t, key, u);
    return node;
  }

}
