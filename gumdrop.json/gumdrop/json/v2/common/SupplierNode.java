package gumdrop.json.v2.common;

import gumdrop.json.v2.AbstractNode;

import java.util.function.Supplier;

public abstract class SupplierNode<T> extends AbstractNode implements Supplier<T> {

  private final T t;

  public SupplierNode(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

}
