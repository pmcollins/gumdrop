package gumdrop.json.v2.common;

import gumdrop.json.v2.AbstractChainable;

import java.util.function.Supplier;

public abstract class Node<T> extends AbstractChainable implements Supplier<T> {

  private final T t;

  public Node(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

}
