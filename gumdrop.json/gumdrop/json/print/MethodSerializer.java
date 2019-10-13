package gumdrop.json.print;

import java.util.function.Function;

public class MethodSerializer<T, U> {

  private final String name;
  private final Function<T, U> method;
  private final JsonSerializer<U> jsonSerializer;

  public MethodSerializer(String name, Function<T, U> method, JsonSerializer<U> jsonSerializer) {
    this.name = name;
    this.method = method;
    this.jsonSerializer = jsonSerializer;
  }

  String getName() {
    return name;
  }

  void print(StringBuilder sb, T t) {
    U u = method.apply(t);
    jsonSerializer.toJson(u, sb);
  }

}
