package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class NullableNode<T, U> extends AbstractChainable {

  private final Node<T> node;
  private final U u;
  private final BiConsumer<U, T> setter;

  public NullableNode(Node<T> node, U u, BiConsumer<U, T> setter) {
    this.node = node;
    this.u = u;
    this.setter = setter;
  }

  @Override
  public Chainable next() {
    setter.accept(u, node.instance());
    return node;
  }

  @Override
  public void nullValue() {
  }

}
