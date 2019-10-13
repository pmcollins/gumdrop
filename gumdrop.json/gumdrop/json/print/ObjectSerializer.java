package gumdrop.json.print;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ObjectSerializer<T> extends JsonSerializer<T> {

  public static class Builder<T> {

    private final List<MethodSerializer<T, ?>> methods = new ArrayList<>();

    public <U> void addSerializer(String name, Function<T, U> method, JsonSerializer<U> jsonSerializer) {
      addSerializer(new MethodSerializer<>(name, method, jsonSerializer));
    }

    public void addSerializer(MethodSerializer<T, ?> methodSerializer) {
      methods.add(methodSerializer);
    }

    protected List<MethodSerializer<T, ?>> getMethods() {
      return methods;
    }

    public ObjectSerializer<T> build() {
      return new ObjectSerializer<>(methods);
    }

  }

  private final List<MethodSerializer<T, ?>> methods;

  protected ObjectSerializer(List<MethodSerializer<T, ?>> methods) {
    this.methods = methods;
  }

  @Override
  public void nonNullToJson(T t, StringBuilder sb) {
    sb.append('{');
    for (int i = 0; i < methods.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      MethodSerializer<T, ?> meth = methods.get(i);
      sb.append('"').append(meth.getName()).append("\":");
      meth.print(sb, t);
    }
    sb.append('}');
  }

}
