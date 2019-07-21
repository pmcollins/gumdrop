package gumdrop.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListNode<T> extends CollectionNode<List<T>, T> {

  public ListNode(Function<Consumer<T>, Node<T>> nodeConstructor) {
    super(ArrayList::new, List::add, nodeConstructor);
  }

}
