package gumdrop.json.print;

import java.util.Map;

public class MapSerializer<T> extends JsonSerializer<Map<String, T>> {

  private final JsonSerializer<T> valuePrinter;

  public MapSerializer(JsonSerializer<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void nonNullToJson(Map<String, T> map, StringBuilder sb) {
    sb.append('{');
    int i = 0;
    for (String key : map.keySet()) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append('"').append(key).append("\":");
      valuePrinter.toJson(map.get(key), sb);
    }
    sb.append('}');
  }

}
