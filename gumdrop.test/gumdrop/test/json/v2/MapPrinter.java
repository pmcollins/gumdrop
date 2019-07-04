package gumdrop.test.json.v2;

import java.util.Map;

class MapPrinter<T> implements JsonPrinter<Map<String, T>> {

  private final JsonPrinter<T> valuePrinter;

  MapPrinter(JsonPrinter<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void print(StringBuilder sb, Map<String, T> map) {
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
