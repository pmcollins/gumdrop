package gumdrop.json.v2.print;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PojoPrinter<T> extends JsonPrinter<T> {

  public static class Builder<T> {

    private final List<MethodPrinter<T, ?>> methods = new ArrayList<>();

    public <U> void addMethodPrinter(String name, Function<T, U> method, JsonPrinter<U> jsonPrinter) {
      addMethodPrinter(new MethodPrinter<>(name, method, jsonPrinter));
    }

    public void addMethodPrinter(MethodPrinter<T, ?> methodPrinter) {
      methods.add(methodPrinter);
    }

    protected List<MethodPrinter<T, ?>> getMethods() {
      return methods;
    }

    public PojoPrinter<T> build() {
      return new PojoPrinter<>(methods);
    }

  }

  private final List<MethodPrinter<T, ?>> methods;

  protected PojoPrinter(List<MethodPrinter<T, ?>> methods) {
    this.methods = methods;
  }

  @Override
  public void print(StringBuilder sb, T t) {
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
