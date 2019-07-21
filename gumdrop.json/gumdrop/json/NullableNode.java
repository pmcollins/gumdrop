package gumdrop.json;

import java.util.function.Consumer;

public class NullableNode<T> extends Node<T> {

  public NullableNode() {
  }

  public NullableNode(Consumer<T> listener) {
    super(listener);
  }

  @Override
  public final void acceptBareword(String bareword) {
    if ("null".equals(bareword)) {
      setValue(null);
    } else {
      acceptNonNullBareword(bareword);
    }
  }

  void acceptNonNullBareword(String bareword) {
    super.acceptBareword(bareword);
  }

}
