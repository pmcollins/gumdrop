package gumdrop.json.v2;

import gumdrop.json.v2.common.Chainable;
import gumdrop.json.v2.common.Node;

public class ArrayNode<T> extends Node<T> {

  private final Binding<T, ?> binding;

  public ArrayNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  @Override
  public final Chainable next() {
    return binding.apply(get());
  }

}
