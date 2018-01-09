package gumdrop.json;

import gumdrop.common.Creator;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsonBuilder<T> {

  private final Getters<T> getters = new Getters<>();
  private final Creator<T> creator;

  public JsonBuilder(Supplier<T> constructor) {
    creator = new Creator<>(constructor);
  }

  public void addStringField(String name, Function<T, String> getter, BiConsumer<T, String> setter) {
    creator.addSetter(name, setter);
    getters.addStringGetter(name, getter);
  }

  public void addIntField(String name, Function<T, Integer> getter, BiConsumer<T, Integer> setter) {
    creator.addIntSetter(name, setter);
    getters.addNumericGetter(name, getter);
  }

  public <U> void addField(String name, Function<T, U> getter, BiConsumer<T, U> setter, Converter<U> converter) {
    creator.addSetter(name, (t, str) -> setter.accept(t, converter.convertFromString(str)));
    getters.addStringGetter(name, (t) -> converter.convertToString(getter.apply(t)));
  }

  public <U> void addSubFields(String name, Function<T, U> fieldGetter, BiConsumer<T, U> fieldSetter, JsonBuilder<U> jsonBuilder) {
    creator.addMember(name, fieldSetter, jsonBuilder.creator);
    getters.addMember(name, fieldGetter, jsonBuilder.getters);
  }

  public String toJson(T t) {
    return getters.getJson(t);
  }

  public T fromJson(String json) {
    BuilderDelegate<T> delegate = new BuilderDelegate<>(creator);
    JsonReader jsonReader = new JsonReader(json, delegate);
    jsonReader.readValue();
    return delegate.getObject();
  }

}
