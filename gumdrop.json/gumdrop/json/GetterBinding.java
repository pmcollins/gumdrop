package gumdrop.json;

import java.util.function.Function;

class GetterBinding<T, U> implements Function<T, String> {

  private final Getters<U> subGetters;
  private final Function<T, U> fieldGetter;

  GetterBinding(Function<T, U> fieldGetter, Getters<U> getters) {
    this.subGetters = getters;
    this.fieldGetter = fieldGetter;
  }

  @Override
  public String apply(T t) {
    return subGetters.getJson(fieldGetter.apply(t));
  }

}
