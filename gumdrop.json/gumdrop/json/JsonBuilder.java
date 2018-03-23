package gumdrop.json;

import gumdrop.common.builder.Builder;

import java.util.function.Supplier;

/**
 * Converts a JSON string to an object of type T.
 * @param <T> the type of the object to be built
 */
public class JsonBuilder<T> extends Builder<T> {

  public JsonBuilder(Supplier<T> constructor) {
    super(constructor);
  }

  public T fromJson(String json) {
    BuilderDelegate<T> delegate = new BuilderDelegate<>(this);
    JsonReader jsonReader = new JsonReader(json, delegate);
    jsonReader.readValue();
    return delegate.getObject();
  }

}
