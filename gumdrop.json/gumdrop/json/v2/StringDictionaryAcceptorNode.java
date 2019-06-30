package gumdrop.json.v2;

public class StringDictionaryAcceptorNode<T> extends AbstractChainable {

  private final T t;
  private final String key;
  private final TriConsumer<T, String, String> triConsumer;

  StringDictionaryAcceptorNode(T t, String key, TriConsumer<T, String, String> triConsumer) {
    this.t = t;
    this.key = key;
    this.triConsumer = triConsumer;
  }

  @Override
  public void accept(String value) {
    triConsumer.accept(t, key, value);
  }

}
