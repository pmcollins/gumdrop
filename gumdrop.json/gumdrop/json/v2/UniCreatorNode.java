package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class UniCreatorNode<T> extends BaseCreatorNode<T> {

  private final BiConsumer<T, String> biConsumer;

  protected UniCreatorNode(T t, BiConsumer<T, String> biConsumer) {
    super(t);
    this.biConsumer = biConsumer;
  }

  @Override
  public final CreatorNode next() {
    return new ConsumerNode<>(get(), biConsumer);
  }

}
