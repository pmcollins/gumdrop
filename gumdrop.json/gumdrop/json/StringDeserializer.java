package gumdrop.json;

import java.util.function.Consumer;

public class StringDeserializer extends NullableDeserializer<String> {

  public StringDeserializer() {
  }

  public StringDeserializer(Consumer<String> listener) {
    super(listener);
  }

  @Override
  public void acceptString(String value) {
    setValue(value);
  }

}
