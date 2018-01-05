package gumdrop.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class Getters<T> {

  private final Map<String, Function<T, String>> getters = new LinkedHashMap<>();

  public void addNumericGetter(String name, Function<T, ?> getter) {
    addBarewordGetter(name, t -> String.valueOf(getter.apply(t)));
  }

  public void addStringGetter(String name, Function<T, String> getter) {
    addBarewordGetter(name, t -> '"' + getter.apply(t) + '"');
  }

  public <U> void addMember(String name, Function<T, U> fieldGetter, Getters<U> subGetters) {
    getters.put(name, new GetterBinding<>(fieldGetter, subGetters));
  }

  private void addBarewordGetter(String name, Function<T, String> getter) {
    getters.put(name, getter);
  }

  public String getJson(T t) {
    StringBuilder sb = new StringBuilder("{");
    int i = 0;
    for (String key : getters.keySet()) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append('"').append(key).append('"').append(':');
      sb.append(getters.get(key).apply(t));
    }
    sb.append('}');
    return sb.toString();
  }

}
