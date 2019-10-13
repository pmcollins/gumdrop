package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CollectionDeserializer<T, U> extends NullableDeserializer<T> {

  private final Supplier<T> constructor;
  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Deserializer<U>> deserializerConstructor;

  public CollectionDeserializer(Supplier<T> constructor, BiConsumer<T, U> method, Function<Consumer<U>, Deserializer<U>> deserializerConstructor) {
    this.constructor = constructor;
    this.method = method;
    this.deserializerConstructor = deserializerConstructor;
  }

  @Override
  public Chainable next() {
    T t = constructor.get();
    setValue(t);
    return new CollectionElementDeserializer<>(t, method, deserializerConstructor);
  }

}
