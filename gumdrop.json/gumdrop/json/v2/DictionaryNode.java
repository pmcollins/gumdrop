package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

public class DictionaryNode<T> extends SupplierNode<T> {

  private final DictionaryBinding<T, ?> dictionaryBinding;

  protected DictionaryNode(T t, DictionaryBinding<T, ?> dictionaryBinding) {
    super(t);
    this.dictionaryBinding = dictionaryBinding;
  }

  @Override
  public Node next(String key) {
    return dictionaryBinding.bind(get(), key);
  }

}
