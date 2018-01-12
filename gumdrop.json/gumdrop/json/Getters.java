package gumdrop.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Getters<T> {

  private final Map<String, BiFunction<T, String, String>> getters = new LinkedHashMap<>();
  private Function<T, Iterable<String>> keyFunction;
  private GetterBinding<T, ?> dynamicBinding;

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
    getters.put(name, (t, key) -> getter.apply(t));
  }

  public void setKeyFunction(Function<T, Iterable<String>> keyFunction) {
    this.keyFunction = keyFunction;
  }

  public <U> void setMemberFunction(BiFunction<T, String, U> dynamicFieldGetter, Getters<U> subGetters) {
    dynamicBinding = new GetterBinding<>(dynamicFieldGetter, subGetters);
  }

  public String getJson(T t) {
    StringBuilder sb = new StringBuilder("{");
    int i = 0;
    Iterable<String> strings = keyFunction == null ? getters.keySet() : keyFunction.apply(t);
    for (String key : strings) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append('"').append(key).append('"').append(':');
      BiFunction<T, String, String> stringFcn = getters.get(key);
      BiFunction<T, String, String> fcn = stringFcn == null ? dynamicBinding : stringFcn;
      String value = fcn.apply(t, key);
      sb.append(value);
    }
    sb.append('}');
    return sb.toString();
  }

}
