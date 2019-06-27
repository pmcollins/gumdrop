package gumdrop.json.v2;

public class CollectionNode<T> extends SupplierNode<T> {

  private final Binding<T, ?> binding;

  protected CollectionNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  @Override
  public final Node next() {
    return binding.bind(get());
  }

}
