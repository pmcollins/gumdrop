package gumdrop.json;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A convenience class for converting Map&lt;String, String&gt;
 */
public class JsonMapConverter extends JsonConverter<Map<String, String>> {

  public JsonMapConverter(Supplier<Map<String, String>> constructor) {
    super(constructor);
    addMapFunctions(Map::put, Map::keySet, Map::get);
  }

}
