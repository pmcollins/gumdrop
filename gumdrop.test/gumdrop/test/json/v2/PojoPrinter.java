package gumdrop.test.json.v2;

import java.util.ArrayList;
import java.util.List;

class PojoPrinter<T> implements JsonPrinter<T> {

  static class Builder<T> {
    private final List<MethodPrinter<T, ?>> methods = new ArrayList<>();
    void addMethodPrinter(MethodPrinter<T, ?> methodPrinter) {
      methods.add(methodPrinter);
    }
    List<MethodPrinter<T, ?>> getMethods() {
      return methods;
    }
    PojoPrinter<T> build() {
      return new PojoPrinter<>(methods);
    }
  }

  private final List<MethodPrinter<T, ?>> methods;

  PojoPrinter(List<MethodPrinter<T, ?>> methods) {
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
