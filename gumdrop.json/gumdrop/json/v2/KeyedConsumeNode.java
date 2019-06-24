package gumdrop.json.v2;

public class KeyedConsumeNode<T> extends AbstractCreatorNode {

  private final T t;
  private final String key;
  private final TriConsumer<T, String, String> triConsumer;

  KeyedConsumeNode(T t, String key, TriConsumer<T, String, String> triConsumer) {
    this.t = t;
    this.key = key;
    this.triConsumer = triConsumer;
  }

  @Override
  public void accept(String value) {
    triConsumer.accept(t, key, value);
  }

}
