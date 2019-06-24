package gumdrop.json.v2;

import java.util.function.Supplier;

public abstract class BaseCreatorNode<T> extends AbstractCreatorNode implements Supplier<T> {

  private final T t;

  BaseCreatorNode(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

}
