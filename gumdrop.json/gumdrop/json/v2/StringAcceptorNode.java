package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class StringAcceptorNode<T> extends AbstractChainable {

  private final T t;
  private final BiConsumer<T, String> method;

  public StringAcceptorNode(T t, BiConsumer<T, String> method) {
    this.t = t;
    this.method = method;
  }

  @Override
  public void accept(String value) {
    method.accept(t, value);
  }

}
