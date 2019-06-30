package gumdrop.json.v2;

public abstract class Node<T> extends AbstractChainable {

  private final T t;

  public Node(T t) {
    this.t = t;
  }

  public T instance() {
    return t;
  }

}
