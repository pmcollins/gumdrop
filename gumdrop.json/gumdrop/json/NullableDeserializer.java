package gumdrop.json;

import java.util.function.Consumer;

public class NullableDeserializer<T> extends Deserializer<T> {

  public NullableDeserializer() {
  }

  public NullableDeserializer(Consumer<T> listener) {
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
