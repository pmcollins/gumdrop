package gumdrop.json;

import java.util.function.Consumer;

public class IntDeserializer extends NullableDeserializer<Integer> {

  public IntDeserializer() {
  }

  public IntDeserializer(Consumer<Integer> listener) {
    super(listener);
  }

  @Override
  void acceptNonNullBareword(String bareword) {
    setValue(Integer.valueOf(bareword));
  }

}
