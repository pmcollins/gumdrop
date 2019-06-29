package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

public class ArrayNode<T> extends SupplierNode<T> {

  private final Binding<T, ?> binding;

  public ArrayNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  @Override
  public final Node next() {
    return binding.apply(get());
  }

}
