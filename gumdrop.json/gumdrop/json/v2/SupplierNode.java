package gumdrop.json.v2;

import java.util.function.Supplier;

public abstract class SupplierNode<T> extends AbstractNode implements Supplier<T> {

  private final T t;

  SupplierNode(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

}

