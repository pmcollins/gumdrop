package gumdrop.json.v2.print;

import java.util.function.Function;

public class MethodPrinter<T, U> {

  private final String name;
  private final Function<T, U> method;
  private final JsonPrinter<U> jsonPrinter;

  public MethodPrinter(String name, Function<T, U> method, JsonPrinter<U> jsonPrinter) {
    this.name = name;
    this.method = method;
    this.jsonPrinter = jsonPrinter;
  }

  String getName() {
    return name;
  }

  void print(StringBuilder sb, T t) {
    U u = method.apply(t);
    jsonPrinter.print(sb, u);
  }

}
