package gumdrop.json.v2;

public class StringDictionaryAcceptorNode<T> extends Node<T> {

  private final String key;
  private final TriConsumer<T, String, String> triConsumer;

  StringDictionaryAcceptorNode(T t, String key, TriConsumer<T, String, String> triConsumer) {
    super(t);
    this.key = key;
    this.triConsumer = triConsumer;
  }

  @Override
  public void acceptString(String value) {
    triConsumer.accept(instance(), key, value);
  }

}
