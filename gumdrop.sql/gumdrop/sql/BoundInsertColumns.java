package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoundInsertColumns<T> {

  private final List<BoundInsertColumn<T, ?>> list = new ArrayList<>();

  public void add(BoundInsertColumn<T, ?> bc) {
    list.add(bc);
  }

  public String getColSql() {
    StringBuilder cols = new StringBuilder();
    StringBuilder bind = new StringBuilder();
    int i = 0;
    for (BoundInsertColumn<T, ?> bc : list) {
      if (bc.isNull()) continue;
      InsertColumn<T, ?> column = bc.getColumn();
      if (i++ > 0) {
        cols.append(',');
        bind.append(',');
      }
      cols.append(column.getName());
      column.appendBind(bind);
    }
    return "(" + cols + ") VALUES (" + bind + ")";
  }

  public void prepare(PreparedStatement ps) throws SQLException {
    for (BoundInsertColumn<T, ?> col : list) {
      col.prepare(ps);
    }
  }

}
