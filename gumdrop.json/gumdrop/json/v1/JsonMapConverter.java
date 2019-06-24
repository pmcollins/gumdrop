package gumdrop.json.v1;

import java.util.HashMap;
import java.util.Map;

/**
 * A convenience class for converting Map&lt;String, String&gt;
 */
public class JsonMapConverter extends JsonConverter<Map<String, String>> {

  public JsonMapConverter() {
    super(HashMap::new);
    addMapFunctions(Map::put, Map::keySet, Map::get);
  }

}
