package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class StringAcceptorNode<T> extends Node<T> {

  private final BiConsumer<T, String> method;

  public StringAcceptorNode(T t, BiConsumer<T, String> method) {
    super(t);
    this.method = method;
  }

  @Override
  public void acceptString(String value) {
    method.accept(instance(), value);
  }

}
