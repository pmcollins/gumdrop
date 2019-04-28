package gumdrop.json2;

import java.util.function.Function;

class GetterBinding<T, U> implements Function<T, String> {

  private final Function<T, U> getter;
  private final Function<U, String> printer;

  GetterBinding(Function<T, U> getter, Function<U, String> printer) {
    this.getter = getter;
    this.printer = printer;
  }

  @Override
  public String apply(T t) {
    U value = getter.apply(t);
    return printer.apply(value);
  }

}
