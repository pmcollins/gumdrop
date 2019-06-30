package gumdrop.json.v2;

public class StringDictionaryNode<T> extends Node<T> {

  private final TriConsumer<T, String, String> triConsumer;

  public StringDictionaryNode(T t, TriConsumer<T, String, String> triConsumer) {
    super(t);
    this.triConsumer = triConsumer;
  }

  @Override
  public final Chainable next(String key) {
    return new StringDictionaryAcceptorNode<>(get(), key, triConsumer);
  }

}
