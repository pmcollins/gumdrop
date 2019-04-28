package gumdrop.json2;

import java.util.List;
import java.util.function.Function;

public class JsonListPrinter<T> implements Function<List<T>, String> {

  private final Function<T, String> fcn;

  public JsonListPrinter(Function<T, String> fcn) {
    this.fcn = fcn;
  }

  @Override
  public String apply(List<T> list) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < list.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      T t = list.get(i);
      String str = fcn.apply(t);
      sb.append(str);
    }
    sb.append(']');
    return sb.toString();
  }

}
