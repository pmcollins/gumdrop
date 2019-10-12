package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldBinding<T, U> {

  private final String name;
  private final BiConsumer<T, U> setter;
  private final Supplier<Deserializer<U>> nodeConstructor;

  public FieldBinding(String name, BiConsumer<T, U> setter, Supplier<Deserializer<U>> nodeConstructor) {
    this.name = name;
    this.setter = setter;
    this.nodeConstructor = nodeConstructor;
  }

  String getName() {
    return name;
  }

  Deserializer<U> buildNode(T t) {
    Consumer<U> listener = value -> setter.accept(t, value);
    Deserializer<U> deserializer = nodeConstructor.get();
    deserializer.setListener(listener);
    return deserializer;
  }

}
