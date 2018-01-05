package gumdrop.json;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Setters<T> {

  private final Map<String, BiConsumer<T, String>> setters = new HashMap<>();
  private final Map<String, SetterBinding<T, ?>> members = new HashMap<>();
  private final Supplier<T> constructor;

  public Setters(Supplier<T> constructor) {
    this.constructor = constructor;
  }

  void addDoubleSetter(String key, BiConsumer<T, Double> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Double.parseDouble(str)));
  }

  protected void addIntSetter(String key, BiConsumer<T, Integer> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Integer.parseInt(str)));
  }

  public void addSetter(String key, BiConsumer<T, String> setter) {
    setters.put(key, setter);
  }

  public <U> void addMember(String name, BiConsumer<T, U> fieldSetter, Setters<U> subSetters) {
    members.put(name, new SetterBinding<>(fieldSetter, subSetters));
  }

  SetterBinding<T, ?> getMember(String name) {
    return members.get(name);
  }

  public T construct() {
    return constructor.get();
  }

  public void apply(T t, String key, String value) {
    setters.get(key).accept(t, value);
  }

  // convenience
  public T fromJson(String json) {
    BuilderDelegate<T> delegate = new BuilderDelegate<>(this);
    JsonReader jsonReader = new JsonReader(json, delegate);
    jsonReader.readValue();
    return delegate.getObject();
  }

}
