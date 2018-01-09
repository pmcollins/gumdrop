package gumdrop.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * A creator of objects of type T. Encapsulates a constructor, and setters.
 */
public class Creator<T> {

  private final Supplier<T> constructor;
  private final Map<String, BiConsumer<T, String>> setters = new HashMap<>();
  private final Map<String, SetterBinding<T, ?>> members = new HashMap<>();

  public Creator(Supplier<T> constructor) {
    this.constructor = constructor;
  }

  void addDoubleSetter(String key, BiConsumer<T, Double> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Double.parseDouble(str)));
  }

  public void addIntSetter(String key, BiConsumer<T, Integer> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Integer.parseInt(str)));
  }

  public void addSetter(String key, BiConsumer<T, String> setter) {
    setters.put(key, setter);
  }

  public <U> void addMember(String name, BiConsumer<T, U> fieldSetter, Creator<U> subSetters) {
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

}
