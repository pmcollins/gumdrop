package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CollectionDeserializer<T, U> extends NullableDeserializer<T> {

  private final Supplier<T> constructor;
  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Deserializer<U>> nodeConstructor;

  public CollectionDeserializer(Supplier<T> constructor, BiConsumer<T, U> method, Function<Consumer<U>, Deserializer<U>> nodeConstructor) {
    this.constructor = constructor;
    this.method = method;
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    T t = constructor.get();
    setValue(t);
    return new CollectionElementDeserializer<>(t, method, nodeConstructor);
  }

}
