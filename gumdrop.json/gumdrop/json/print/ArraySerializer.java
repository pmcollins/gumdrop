package gumdrop.json.print;

import java.util.List;

public class ArraySerializer<T> extends JsonSerializer<List<T>> {

  private final JsonSerializer<T> valuePrinter;

  public ArraySerializer(JsonSerializer<T> valuePrinter) {
    this.valuePrinter = valuePrinter;
  }

  @Override
  public void nonNullToJson(List<T> list, StringBuilder sb) {
    printList(sb, list, valuePrinter);
  }

  public static <T> void printList(
    StringBuilder sb,
    List<T> list,
    JsonSerializer<T> valuePrinter
  ) {
    sb.append('[');
    for (int i = 0; i < list.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      valuePrinter.toJson(list.get(i), sb);
    }
    sb.append(']');
  }

}
