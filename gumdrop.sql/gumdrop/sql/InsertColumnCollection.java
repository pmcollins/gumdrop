package gumdrop.sql;

import revlab.core.entities.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InsertColumnCollection<T extends Entity> implements Iterable<InsertColumn<T, ?>> {

  private final List<InsertColumn<T, ?>> list = new ArrayList<>();

  public void add(InsertColumn<T, ?> column) {
    list.add(column);
  }

  @Override
  public Iterator<InsertColumn<T, ?>> iterator() {
    return list.iterator();
  }

  public BoundInsertColumns<T> getBoundSubset(T t) {
    BoundInsertColumns<T> boundColumns = new BoundInsertColumns<>();
    int i = 1;
    for (InsertColumn<T, ?> column : list) {
      BoundInsertColumn<T, ?> bc = new BoundInsertColumn<>(column, t);
      if (!bc.isNull()) {
        bc.setIdx(i++);
        boundColumns.add(bc);
      }
    }
    return boundColumns;
  }

}
