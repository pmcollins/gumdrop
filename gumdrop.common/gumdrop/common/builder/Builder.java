package gumdrop.common.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * A creator of objects of type T. Encapsulates a constructor and setters.
 */
public class Builder<T> {

  private final Supplier<T> constructor;
  private final Map<String, TriConsumer<T, String>> setters = new HashMap<>();
  private final Map<String, SetterBinding<T, ?>> members = new HashMap<>();

  public Builder(Supplier<T> constructor) {
    this.constructor = constructor;
  }

  void addDoubleSetter(String key, BiConsumer<T, Double> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Double.parseDouble(str)));
  }

  public void addIntSetter(String key, BiConsumer<T, Integer> setter) {
    addSetter(key, (t, str) -> setter.accept(t, Integer.parseInt(str)));
  }

  public void addSetter(String key, BiConsumer<T, String> setter) {
    setters.put(key, (t, k, v) -> setter.accept(t, v));
  }

  public void addSetter(String key, TriConsumer<T, String> setter) {
    setters.put(key, setter);
  }

  public <U> void addMember(String name, BiConsumer<T, U> fieldSetter, Builder<U> subSetters) {
    members.put(name, new SetterBinding<>(fieldSetter, subSetters));
  }

  public <U> void addMapMember(String name, TriConsumer<T, U> fieldSetter, Builder<U> subSetters) {
    members.put(name, new SetterBinding<>(fieldSetter, subSetters));
  }

  SetterBinding<T, ?> getMember(String name) {
    SetterBinding<T, ?> namedMember = members.get(name);
    return namedMember != null ? namedMember : members.get("*");
  }

  public T construct() {
    return constructor.get();
  }

  public void apply(T t, String key, String value) {
    TriConsumer<T, String> setter = setters.get(key);
    if (setter != null) {
      setter.accept(t, key, value);
    } else {
      setters.get("*").accept(t, key, value);
    }
  }

}
