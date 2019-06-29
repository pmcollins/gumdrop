package gumdrop.json.v2;

import gumdrop.json.v2.common.StringAcceptorNode;
import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

import java.util.function.BiConsumer;

public class StringArrayNode<T> extends SupplierNode<T> {

  private final BiConsumer<T, String> method;

  protected StringArrayNode(T t, BiConsumer<T, String> method) {
    super(t);
    this.method = method;
  }

  @Override
  public final Node next() {
    return new StringAcceptorNode<>(get(), method);
  }

}
