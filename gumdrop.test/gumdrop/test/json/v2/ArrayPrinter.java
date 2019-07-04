package gumdrop.test.json.v2;

import java.util.List;

class ArrayPrinter<T> implements JsonPrinter<List<T>> {

  private final JsonPrinter<T> valuePrinter;

  ArrayPrinter(JsonPrinter<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void print(StringBuilder sb, List<T> list) {
    sb.append('[');
    for (int i = 0; i < list.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      valuePrinter.print(sb, list.get(i));
    }
    sb.append(']');
  }

}
