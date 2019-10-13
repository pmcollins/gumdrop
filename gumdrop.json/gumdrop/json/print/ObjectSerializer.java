package gumdrop.json.print;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ObjectSerializer<T> extends JsonSerializer<T> {

  private final List<MethodSerializer<T, ?>> methodSerializers = new ArrayList<>();

  protected <U> void addMethodSerializer(String name, Function<T, U> method, JsonSerializer<U> jsonSerializer) {
    addMethodSerializer(new MethodSerializer<>(name, method, jsonSerializer));
  }

  protected void addMethodSerializer(MethodSerializer<T, ?> methodSerializer) {
    methodSerializers.add(methodSerializer);
  }

  @Override
  public void nonNullToJson(T t, StringBuilder sb) {
    sb.append('{');
    for (int i = 0; i < methodSerializers.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      MethodSerializer<T, ?> meth = methodSerializers.get(i);
      sb.append('"').append(meth.getName()).append("\":");
      meth.print(sb, t);
    }
    sb.append('}');
  }

}
