package gumdrop.json.v2;

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
