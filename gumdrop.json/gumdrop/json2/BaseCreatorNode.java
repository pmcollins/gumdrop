package gumdrop.json2;

import java.util.function.Supplier;

public abstract class BaseCreatorNode<T> extends AbstractBuilderNode implements Supplier<T> {

  private final T t;

  BaseCreatorNode(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

}
