package gumdrop.json.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListNode<T> extends NullableNode<List<T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  public ListNode(Function<Consumer<T>, Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    List<T> list = new ArrayList<>();
    setValue(list);
    return new ListElementNode<>(list, nodeConstructor);
  }

}
