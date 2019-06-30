package gumdrop.json.v2;

import gumdrop.json.v2.common.Chainable;
import gumdrop.json.v2.common.Node;

import java.util.function.Supplier;

public class DictionaryBinding<T, U> {

  private final Supplier<Node<U>> creatorNodeSupplier;
  private final TriConsumer<T, String, U> putMethod;

  public DictionaryBinding(Supplier<Node<U>> creatorNodeSupplier, TriConsumer<T, String, U> putMethod) {
    this.creatorNodeSupplier = creatorNodeSupplier;
    this.putMethod = putMethod;
  }

  Chainable bind(T t, String key) {
    Node<U> node = creatorNodeSupplier.get();
    U u = node.get();
    putMethod.accept(t, key, u);
    return node;
  }

}
