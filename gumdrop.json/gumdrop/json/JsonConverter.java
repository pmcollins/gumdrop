package gumdrop.json;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * JSON (de)serialization. This is just a facade for JsonWriter and JsonBuilder. To go just one direction, use
 * one of those instead.
 * @param <T> The type of object to serialize/deserialize to/from JSON
 */
public class JsonConverter<T> {

  private final JsonWriter<T> jsonWriter = new JsonWriter<>();
  private final JsonBuilder<T> builder;

  public JsonConverter(Supplier<T> constructor) {
    builder = new JsonBuilder<>(constructor);
  }

  public void addStringField(String name, Function<T, String> getter, BiConsumer<T, String> setter) {
    builder.addSetter(name, setter);
    jsonWriter.addStringGetter(name, getter);
  }

  public void addIntField(String name, Function<T, Integer> getter, BiConsumer<T, Integer> setter) {
    builder.addIntSetter(name, setter);
    jsonWriter.addNumericGetter(name, getter);
  }

  public <U> void addField(String name, Function<T, U> getter, BiConsumer<T, U> setter, Converter<U> converter) {
    builder.addSetter(name, (t, str) -> setter.accept(t, converter.convertFromString(str)));
    jsonWriter.addStringGetter(name, (t) -> converter.convertToString(getter.apply(t)));
  }

  public <U> void addSubFields(String name, Function<T, U> fieldGetter, BiConsumer<T, U> fieldSetter, JsonConverter<U> jsonConverter) {
    builder.addMember(name, fieldSetter, jsonConverter.builder);
    jsonWriter.addMember(name, fieldGetter, jsonConverter.jsonWriter);
  }

  public String toJson(T t) {
    return jsonWriter.toJson(t);
  }

  public T fromJson(String json) {
    return builder.fromJson(json);
  }

}
