package gumdrop.json.v2.print;

import java.util.Map;

public class MapPrinter<T> extends JsonPrinter<Map<String, T>> {

  private final JsonPrinter<T> valuePrinter;

  public MapPrinter(JsonPrinter<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void printNonNull(StringBuilder sb, Map<String, T> map) {
    sb.append('{');
    int i = 0;
    for (String key : map.keySet()) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append('"').append(key).append("\":");
      valuePrinter.print(sb, map.get(key));
    }
    sb.append('}');
  }

}
