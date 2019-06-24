package gumdrop.json.v2;

public class KeyedCreatorNode<T> extends BaseCreatorNode<T> {

  private final TriConsumer<T, String, String> triConsumer;

  public KeyedCreatorNode(T t, TriConsumer<T, String, String> triConsumer) {
    super(t);
    this.triConsumer = triConsumer;
  }

  @Override
  public final CreatorNode next(String key) {
    return new KeyedConsumeNode<>(get(), key, triConsumer);
  }

}
