package gumdrop.json.v2;

public abstract class Node<T> extends BaseChainable {

  private T t;

  public Node(T t) {
    this.t = t;
  }

  public Node() {
  }

  public void setInstance(T t) {
    this.t = t;
  }

  public T instance() {
    return t;
  }

}
