package gumdrop.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class SelectColumns<T> implements Iterable<SelectColumn<T, ?>> {

  private final List<SelectColumn<T, ?>> list = new ArrayList<>();
  private final Supplier<T> constructor;

  public SelectColumns(Supplier<T> constructor) {
    this.constructor = constructor;
  }

  T createInstance() {
    return constructor.get();
  }

  public void add(SelectColumn<T, ?> column) {
    list.add(column);
  }

  @Override
  public Iterator<SelectColumn<T, ?>> iterator() {
    return list.iterator();
  }

}
