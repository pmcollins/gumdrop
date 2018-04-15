package gumdrop.json;

import java.util.function.BiFunction;
import java.util.function.Function;

class GetterBinding<T, U> implements BiFunction<T, String, String> {

  private final Function<U, String> stringFunction;
  private final BiFunction<T, String, U> fieldGetter;

  GetterBinding(Function<T, U> fieldGetter, Function<U, String> stringFunction) {
    this.stringFunction = stringFunction;
    this.fieldGetter = (t, s) -> fieldGetter.apply(t);
  }

  GetterBinding(BiFunction<T, String, U> fieldGetter, Function<U, String> jsonWriter) {
    this.stringFunction = jsonWriter;
    this.fieldGetter = fieldGetter;
  }

  @Override
  public String apply(T t, String key) {
    return stringFunction.apply(fieldGetter.apply(t, key));
  }

}
