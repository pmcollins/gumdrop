package gumdrop.json;

import java.util.function.BiConsumer;

public class IntFieldBinding<T> extends FieldBinding<T, Integer> {

  public IntFieldBinding(String name, BiConsumer<T, Integer> setter) {
    super(name, setter, IntDeserializer::new);
  }

}
