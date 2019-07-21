package gumdrop.json;

import java.util.function.Consumer;

public class IntNode extends NullableNode<Integer> {

  public IntNode() {
  }

  public IntNode(Consumer<Integer> listener) {
    super(listener);
  }

  @Override
  void acceptNonNullBareword(String bareword) {
    setValue(Integer.valueOf(bareword));
  }

}
