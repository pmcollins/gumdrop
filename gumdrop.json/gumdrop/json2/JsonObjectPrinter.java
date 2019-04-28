package gumdrop.json2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JsonObjectPrinter<T> implements Function<T, String> {

  private final Map<String, Function<T, String>> getters;

  private JsonObjectPrinter(Map<String, Function<T, String>> getters) {
    this.getters = getters;
  }

  @Override
  public String apply(T t) {
    if (t == null) return "null";
    StringBuilder sb = new StringBuilder("{");
    int i = 0;
    for (String name : getters.keySet()) {
      if (i++ > 0) {
        sb.append(',');
      }
      Function<T, String> getter = getters.get(name);
      String value = getter.apply(t);
      sb.append('"').append(name).append("\":");
      sb.append(value);
    }
    sb.append('}');
    return sb.toString();
  }

  public static class Builder<T> {

    private final Map<String, Function<T, String>> getters = new LinkedHashMap<>();

    /**
     * Useful for a getter of any object that can be rendered with String.valueOf(). Value not wrapped in quotes.
     * If you're trying to extract maximum performance and have a primitive, don't use this because this will box the primitive first.
     *
     * @param name   used as the json key
     * @param getter the primitive getter
     */
    public void addStringValueOfGetter(String name, Function<T, ?> getter) {
      addUnquotedGetter(name, t -> {
        Object object = getter.apply(t);
        return String.valueOf(object);
      });
    }

    /**
     * Calls the getter and wraps the value in quotes.
     *
     * @param name   used as the json key
     * @param getter the String getter
     */
    public void addQuotedGetter(String name, Function<T, String> getter) {
      addUnquotedGetter(name, t -> '"' + getter.apply(t) + '"');
    }

    /**
     * Just calls the getter without wrapping the value in quotes. You'll end up with e.g. {"foo":42}.
     *
     * @param name   used as the json key
     * @param getter the String getter
     */
    public void addUnquotedGetter(String name, Function<T, String> getter) {
      getters.put(name, getter);
    }

    /**
     * If your type T has a list of U member, use this to turn it into JSON. You'll end up with e.g. {"foo":[1,2,3]}
     *
     * @param name the name of the field. Will show up in the JSON e.g. {"theNameOfTheField":[]}
     * @param listGetter a function that will return a List of U given an instance of T
     * @param subListPrinter a JsonListPrinter that will turn said list into a JSON string
     * @param <U> the type of objects inside the list, which is being turned into JSON
     */
    public <U> void addSubPrinter(String name, Function<T, List<U>> listGetter, JsonListPrinter<U> subListPrinter) {
      GetterBinding<T, List<U>> listGetterBinding = new GetterBinding<>(listGetter, subListPrinter);
      getters.put(name, listGetterBinding);
    }

    /**
     * If your type T has a U member, use this to turn it into JSON. You'll end up with e.g. {"foo":{"bar":"baz}}
     *
     * @param name the name of the field. Will show up in the JSON e.g. {"theNameOfTheField":null}
     * @param getter a function that will return a U given an instance of T
     * @param subObjectPrinter a JsonObjectPrinter that will turn said object into a JSON string
     * @param <U> the type of the sub field/member being turned into JSON
     */
    public <U> void addSubPrinter(String name, Function<T, U> getter, JsonObjectPrinter<U> subObjectPrinter) {
      GetterBinding<T, U> getterBinding = new GetterBinding<>(getter, subObjectPrinter);
      getters.put(name, getterBinding);
    }

    public JsonObjectPrinter<T> build() {
      return new JsonObjectPrinter<>(getters);
    }

  }

}
