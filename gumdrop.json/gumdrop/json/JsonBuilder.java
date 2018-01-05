package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsonBuilder<T> {

  private final Getters<T> getters = new Getters<>();
  private final Setters<T> setters;

  public JsonBuilder(Supplier<T> constructor) {
    setters = new Setters<>(constructor);
  }

  public void addStringField(String name, Function<T, String> getter, BiConsumer<T, String> setter) {
    setters.addSetter(name, setter);
    getters.addStringGetter(name, getter);
  }

  public void addIntField(String name, Function<T, Integer> getter, BiConsumer<T, Integer> setter) {
    setters.addIntSetter(name, setter);
    getters.addNumericGetter(name, getter);
  }

  public <U> void addField(String name, Function<T, U> getter, BiConsumer<T, U> setter, Converter<U> converter) {
    setters.addSetter(name, (t, str) -> setter.accept(t, converter.convertFromString(str)));
    getters.addStringGetter(name, (t) -> converter.convertToString(getter.apply(t)));
  }

  public <U> void addSubFields(String name, Function<T, U> fieldGetter, BiConsumer<T, U> fieldSetter, JsonBuilder<U> jsonBuilder) {
    setters.addMember(name, fieldSetter, jsonBuilder.setters);
    getters.addMember(name, fieldGetter, jsonBuilder.getters);
  }

  public String toJson(T t) {
    return getters.getJson(t);
  }

  public T fromJson(String json) {
    return setters.fromJson(json);
  }

}
