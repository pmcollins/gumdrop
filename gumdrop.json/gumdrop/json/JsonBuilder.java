package gumdrop.json;

import gumdrop.common.Builder;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsonBuilder<T> {

  private final Getters<T> getters = new Getters<>();
  private final Builder<T> builder;

  public JsonBuilder(Supplier<T> constructor) {
    builder = new Builder<>(constructor);
  }

  public void addStringField(String name, Function<T, String> getter, BiConsumer<T, String> setter) {
    builder.addSetter(name, setter);
    getters.addStringGetter(name, getter);
  }

  public void addIntField(String name, Function<T, Integer> getter, BiConsumer<T, Integer> setter) {
    builder.addIntSetter(name, setter);
    getters.addNumericGetter(name, getter);
  }

  public <U> void addField(String name, Function<T, U> getter, BiConsumer<T, U> setter, Converter<U> converter) {
    builder.addSetter(name, (t, str) -> setter.accept(t, converter.convertFromString(str)));
    getters.addStringGetter(name, (t) -> converter.convertToString(getter.apply(t)));
  }

  public <U> void addSubFields(String name, Function<T, U> fieldGetter, BiConsumer<T, U> fieldSetter, JsonBuilder<U> jsonBuilder) {
    builder.addMember(name, fieldSetter, jsonBuilder.builder);
    getters.addMember(name, fieldGetter, jsonBuilder.getters);
  }

  public String toJson(T t) {
    return getters.getJson(t);
  }

  public T fromJson(String json) {
    return JsonReader.fromJson(json, builder);
  }

}
