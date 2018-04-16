package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoundInsertColumn<T, U> {

  private final U u;
  private final InsertColumn<T, U> column;
  private int idx;

  BoundInsertColumn(InsertColumn<T, U> column, T t) {
    this.column = column;
    u = column.applyGetter(t);
  }

  public boolean isNull() {
    return u == null;
  }

  public InsertColumn<T, U> getColumn() {
    return column;
  }

  public void prepare(PreparedStatement ps) throws SQLException {
    column.set(ps, idx, u);
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

}
