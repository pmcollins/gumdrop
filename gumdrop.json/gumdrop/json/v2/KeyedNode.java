package gumdrop.json.v2;

public class KeyedNode<T> extends SupplierNode<T> {

  private final TriConsumer<T, String, String> triConsumer;

  public KeyedNode(T t, TriConsumer<T, String, String> triConsumer) {
    super(t);
    this.triConsumer = triConsumer;
  }

  @Override
  public final Node next(String key) {
    return new KeyedConsumeNode<>(get(), key, triConsumer);
  }

}
