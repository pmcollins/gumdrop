package gumdrop.json.v2;

public class NullableRootNode<T> extends Node<T> {

  private final Chainable node;
  private boolean nullValue;

  public NullableRootNode(Node<T> node) {
    super(node.instance());
    this.node = node;
  }

  @Override
  public Chainable next() {
    return node;
  }

  @Override
  public void nullValue() {
    this.nullValue = true;
  }

  @Override
  public T instance() {
    return nullValue ? null : super.instance();
  }

}
