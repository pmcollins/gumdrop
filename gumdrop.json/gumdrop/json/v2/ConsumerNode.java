package gumdrop.json.v2;

import java.util.function.BiConsumer;

class ConsumerNode<T> extends AbstractNode {

  private final T t;
  private final BiConsumer<T, String> biConsumer;

  ConsumerNode(T t, BiConsumer<T, String> biConsumer) {
    this.t = t;
    this.biConsumer = biConsumer;
  }

  @Override
  public void accept(String value) {
    biConsumer.accept(t, value);
  }

}
