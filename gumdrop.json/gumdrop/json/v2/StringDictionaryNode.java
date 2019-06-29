package gumdrop.json.v2;

import gumdrop.json.v2.common.StringDictionaryAcceptorNode;
import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

public class StringDictionaryNode<T> extends SupplierNode<T> {

  private final TriConsumer<T, String, String> triConsumer;

  public StringDictionaryNode(T t, TriConsumer<T, String, String> triConsumer) {
    super(t);
    this.triConsumer = triConsumer;
  }

  @Override
  public final Node next(String key) {
    return new StringDictionaryAcceptorNode<>(get(), key, triConsumer);
  }

}
