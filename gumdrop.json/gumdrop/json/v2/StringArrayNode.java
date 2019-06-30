package gumdrop.json.v2;

import gumdrop.json.v2.common.StringAcceptorNode;
import gumdrop.json.v2.common.Chainable;
import gumdrop.json.v2.common.Node;

import java.util.function.BiConsumer;

public class StringArrayNode<T> extends Node<T> {

  private final BiConsumer<T, String> method;

  protected StringArrayNode(T t, BiConsumer<T, String> method) {
    super(t);
    this.method = method;
  }

  @Override
  public final Chainable next() {
    return new StringAcceptorNode<>(get(), method);
  }

}
