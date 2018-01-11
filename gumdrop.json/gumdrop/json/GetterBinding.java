package gumdrop.json;

import java.util.function.BiFunction;
import java.util.function.Function;

class GetterBinding<T, U> implements BiFunction<T, String, String> {

  private final Getters<U> subGetters;
  private final BiFunction<T, String, U> fieldGetter;

  GetterBinding(Function<T, U> fieldGetter, Getters<U> getters) {
    this.subGetters = getters;
    this.fieldGetter = (t, s) -> fieldGetter.apply(t);
  }

  GetterBinding(BiFunction<T, String, U> fieldGetter, Getters<U> getters) {
    this.subGetters = getters;
    this.fieldGetter = fieldGetter;
  }

  @Override
  public String apply(T t, String key) {
    return subGetters.getJson(fieldGetter.apply(t, key));
  }

}
