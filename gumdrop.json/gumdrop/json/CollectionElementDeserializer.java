package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class CollectionElementDeserializer<T, U> extends Deserializer<T> {

  private final BiConsumer<T, U> method;
  private final Function<Consumer<U>, Deserializer<U>> constructorWithCallback;
  private final Supplier<Deserializer<U>> constructor;

  CollectionElementDeserializer(T t, BiConsumer<T, U> method, Function<Consumer<U>, Deserializer<U>> constructorWithCallback) {
    super(t);
    this.method = method;
    this.constructorWithCallback = constructorWithCallback;
    constructor = null;
  }

  CollectionElementDeserializer(T t, BiConsumer<T, U> method, Supplier<Deserializer<U>> constructor) {
    super(t);
    this.method = method;
    this.constructor = constructor;
    constructorWithCallback = null;
  }

  @Override
  public Chainable next() {
    Consumer<U> listener = u -> {
      T t = getValue();
      method.accept(t, u);
    };
    if (constructorWithCallback != null) {
      return constructorWithCallback.apply(listener);
    } else if (constructor != null) {
      Deserializer<U> deserializer = constructor.get();
      deserializer.setListener(listener);
      return deserializer;
    }
    return null;
  }

}
