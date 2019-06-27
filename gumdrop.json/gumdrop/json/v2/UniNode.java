package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class UniNode<T> extends SupplierNode<T> {

  private final BiConsumer<T, String> biConsumer;

  protected UniNode(T t, BiConsumer<T, String> biConsumer) {
    super(t);
    this.biConsumer = biConsumer;
  }

  @Override
  public final Node next() {
    return new ConsumerNode<>(get(), biConsumer);
  }

}
