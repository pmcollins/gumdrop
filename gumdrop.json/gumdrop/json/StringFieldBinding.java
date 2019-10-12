package gumdrop.json;

import java.util.function.BiConsumer;

public class StringFieldBinding<T> extends FieldBinding<T, String> {

  public StringFieldBinding(Enum<?> e, BiConsumer<T, String> setter) {
    this(e.name().toLowerCase(), setter);
  }

  public StringFieldBinding(String name, BiConsumer<T, String> setter) {
    super(name, setter, StringDeserializer::new);
  }

}
