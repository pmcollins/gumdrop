package gumdrop.json.v2;

public class ArrayNode<T> extends Node<T> {

  private Binding<T, ?> binding;

  public ArrayNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  public ArrayNode() {
  }

  protected void setBinding(Binding<T, ?> binding) {
    this.binding = binding;
  }

  @Override
  public final Chainable next() {
    return binding.apply(instance());
  }

}
