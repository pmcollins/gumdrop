package gumdrop.json.v2;

import gumdrop.json.v2.common.Chainable;
import gumdrop.json.v2.common.Node;

public class DictionaryNode<T> extends Node<T> {

  private final DictionaryBinding<T, ?> dictionaryBinding;

  protected DictionaryNode(T t, DictionaryBinding<T, ?> dictionaryBinding) {
    super(t);
    this.dictionaryBinding = dictionaryBinding;
  }

  @Override
  public Chainable next(String key) {
    return dictionaryBinding.bind(get(), key);
  }

}
