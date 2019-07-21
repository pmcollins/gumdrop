package gumdrop.json;

import java.util.function.Consumer;

public class Node<T> extends BaseChainable {

  private T value;
  private Consumer<T> listener;

  public Node() {
  }

  public Node(T value) {
    this.value = value;
  }

  Node(Consumer<T> listener) {
    this.listener = listener;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    if (listener != null) {
      listener.accept(value);
    }
    this.value = value;
  }

  public void setListener(Consumer<T> listener) {
    this.listener = listener;
  }

}
