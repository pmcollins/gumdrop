package gumdrop.json;

import java.util.function.Consumer;

public class StringNode extends NullableNode<String> {

  public StringNode() {
  }

  public StringNode(Consumer<String> listener) {
    super(listener);
  }

  @Override
  public void acceptString(String value) {
    setValue(value);
  }

}
