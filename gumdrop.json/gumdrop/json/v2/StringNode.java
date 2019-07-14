package gumdrop.json.v2;

import java.util.function.Consumer;

public class StringNode extends NullableNode<String> {

  public StringNode(Consumer<String> listener) {
    super(listener);
  }

  @Override
  public void acceptString(String value) {
    setValue(value);
  }

}
