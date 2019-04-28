package gumdrop.json;

import gumdrop.common.builder.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * JSON serialization and deserialization. This is just a facade for JsonWriter and JsonBuilder. To just serialize or
 * just deserialize, use one of those instead.
 * @param <T> The type of object to serialize/deserialize to/from JSON
 */
public class JsonConverter<T> implements StringConverter<T> {

  private final JsonWriter<T> jsonWriter = new JsonWriter<>();
  private final JsonBuilder<T> builder;

  public JsonConverter(Supplier<T> constructor) {
    builder = new JsonBuilder<>(constructor);
  }

  public void addField(String name, Function<T, String> getter, BiConsumer<T, String> setter) {
    builder.addSetter(name, setter);
    jsonWriter.addStringGetter(name, getter);
  }

  public void addIntField(String name, Function<T, Integer> getter, BiConsumer<T, Integer> setter) {
    builder.addIntSetter(name, setter);
    jsonWriter.addNumericGetter(name, getter);
  }

  public <U> void addField(String name, Function<T, U> getter, BiConsumer<T, U> setter, StringConverter<U> stringConverter) {
    builder.addSetter(name, (t, str) -> setter.accept(t, stringConverter.fromString(str)));
    jsonWriter.addStringGetter(name, (t) -> stringConverter.toString(getter.apply(t)));
  }

  public <U> void addSubConverter(String name, Function<T, U> fieldGetter, BiConsumer<T, U> fieldSetter, JsonConverter<U> jsonConverter) {
    builder.addBuilder(name, fieldSetter, jsonConverter.builder);
    jsonWriter.addSubWriter(name, fieldGetter, jsonConverter.jsonWriter);
  }

  public void addMapFunctions(
    TriConsumer<T, String> tc,
    Function<T, Iterable<String>> keyFunction,
    BiFunction<T, String, String> dynamicFieldGetter
  ) {
    builder.addCatchallSetter(tc);
    jsonWriter.setMapFunctions(keyFunction, dynamicFieldGetter);
  }

  @Override
  public String toString(T t) {
    return jsonWriter.apply(t);
  }

  @Override
  public T fromString(String json) {
    return builder.fromJson(json);
  }
}
