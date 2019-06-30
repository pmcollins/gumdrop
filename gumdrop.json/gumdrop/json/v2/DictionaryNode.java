package gumdrop.json.v2;

public class DictionaryNode<T> extends Node<T> {

  private final DictionaryBinding<T, ?> dictionaryBinding;

  protected DictionaryNode(T t, DictionaryBinding<T, ?> dictionaryBinding) {
    super(t);
    this.dictionaryBinding = dictionaryBinding;
  }

  @Override
  public Chainable next(String key) {
    return dictionaryBinding.bind(instance(), key);
  }

}
