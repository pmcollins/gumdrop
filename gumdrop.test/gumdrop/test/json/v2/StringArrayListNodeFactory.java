package gumdrop.test.json.v2;

import gumdrop.json.v2.ArrayLeafNode;
import gumdrop.json.v2.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

class StringArrayListNodeFactory implements Supplier<Node<List<String>>> {

  private final BiConsumer<List<String>, String> add = List::add;

  @Override
  public Node<List<String>> get() {
    return new ArrayLeafNode<>(new ArrayList<>(), add);
  }

}
