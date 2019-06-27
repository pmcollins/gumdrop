package gumdrop.json.v2;

public class TriBindingNode<T> extends SupplierNode<T> {

  private final TriBinding<T, ?> triBinding;

  protected TriBindingNode(T t, TriBinding<T, ?> triBinding) {
    super(t);
    this.triBinding = triBinding;
  }

  @Override
  public Node next(String key) {
    return triBinding.bind(get(), key);
  }

}
