package gumdrop.json;

import java.util.function.BiFunction;
import java.util.function.Function;

class GetterBinding<T, U> implements BiFunction<T, String, String> {

  private final JsonWriter<U> subJsonWriter;
  private final BiFunction<T, String, U> fieldGetter;

  GetterBinding(Function<T, U> fieldGetter, JsonWriter<U> jsonWriter) {
    this.subJsonWriter = jsonWriter;
    this.fieldGetter = (t, s) -> fieldGetter.apply(t);
  }

  GetterBinding(BiFunction<T, String, U> fieldGetter, JsonWriter<U> jsonWriter) {
    this.subJsonWriter = jsonWriter;
    this.fieldGetter = fieldGetter;
  }

  @Override
  public String apply(T t, String key) {
    return subJsonWriter.toJson(fieldGetter.apply(t, key));
  }

}
