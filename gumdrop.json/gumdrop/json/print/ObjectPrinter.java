package gumdrop.json.print;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ObjectPrinter<T> extends JsonPrinter<T> {

  public static class Builder<T> {

    private final List<MethodPrinter<T, ?>> methods = new ArrayList<>();

    public <U> void addPrinter(String name, Function<T, U> method, JsonPrinter<U> jsonPrinter) {
      addPrinter(new MethodPrinter<>(name, method, jsonPrinter));
    }

    public void addPrinter(MethodPrinter<T, ?> methodPrinter) {
      methods.add(methodPrinter);
    }

    protected List<MethodPrinter<T, ?>> getMethods() {
      return methods;
    }

    public ObjectPrinter<T> build() {
      return new ObjectPrinter<>(methods);
    }

  }

  private final List<MethodPrinter<T, ?>> methods;

  protected ObjectPrinter(List<MethodPrinter<T, ?>> methods) {
    this.methods = methods;
  }

  @Override
  public void printNonNull(StringBuilder sb, T t) {
    sb.append('{');
    for (int i = 0; i < methods.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      MethodPrinter<T, ?> meth = methods.get(i);
      sb.append('"').append(meth.getName()).append("\":");
      meth.print(sb, t);
    }
    sb.append('}');
  }

}
