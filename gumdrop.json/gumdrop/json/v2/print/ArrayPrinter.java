package gumdrop.json.v2.print;

import java.util.List;

public class ArrayPrinter<T> extends JsonPrinter<List<T>> {

  private final JsonPrinter<T> valuePrinter;

  public ArrayPrinter(JsonPrinter<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void print(StringBuilder sb, List<T> list) {
    printList(sb, list, valuePrinter);
  }

  public static <T> void printList(
    StringBuilder sb,
    List<T> list,
    JsonPrinter<T> valuePrinter
  ) {
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
