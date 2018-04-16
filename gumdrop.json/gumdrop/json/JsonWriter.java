package gumdrop.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Converts an object to a JSON string.
 * @param <T> the type of object to serialize to JSON
 */
public class JsonWriter<T> implements Function<T, String> {

  private final Map<String, BiFunction<T, String, String>> getters = new LinkedHashMap<>();
  private Function<T, Iterable<String>> keyFunction;
  private GetterBinding<T, ?> catchallBinding;

  public void addNumericGetter(String name, Function<T, ?> getter) {
    addBarewordGetter(name, t -> String.valueOf(getter.apply(t)));
  }

  public void addStringGetter(String name, Function<T, String> getter) {
    addBarewordGetter(name, t -> '"' + getter.apply(t) + '"');
  }

  public <U> void addMember(String name, Function<T, U> fieldGetter, JsonWriter<U> subJsonWriter) {
    getters.put(name, new GetterBinding<>(fieldGetter, subJsonWriter));
  }

  private void addBarewordGetter(String name, Function<T, String> getter) {
    getters.put(name, (t, key) -> getter.apply(t));
  }

  public void setMapFunctions(Function<T, Iterable<String>> keyFunction, BiFunction<T, String, String> dynamicFieldGetter) {
    setKeyFunction(keyFunction);
    setCatchallStringBinding(dynamicFieldGetter);
  }

  public void setKeyFunction(Function<T, Iterable<String>> keyFunction) {
    this.keyFunction = keyFunction;
  }

  public void setCatchallStringBinding(BiFunction<T, String, String> dynamicFieldGetter) {
    setCatchallBinding(dynamicFieldGetter, s -> '"' + s + '"');
  }

  public <U> void setCatchallBinding(BiFunction<T, String, U> catchallFieldGetter, Function<U, String> stringFunction) {
    catchallBinding = new GetterBinding<>(catchallFieldGetter, stringFunction);
  }

  @Override
  public String apply(T t) {
    StringBuilder sb = new StringBuilder("{");
    int i = 0;
    Iterable<String> keys = keyFunction == null ? getters.keySet() : keyFunction.apply(t);
    for (String key : keys) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append('"').append(key).append('"').append(':');
      BiFunction<T, String, String> stringFcn = getters.get(key);
      BiFunction<T, String, String> fcn = stringFcn == null ? catchallBinding : stringFcn;
      String value = fcn.apply(t, key);
      sb.append(value);
    }
    sb.append('}');
    return sb.toString();
  }
}
