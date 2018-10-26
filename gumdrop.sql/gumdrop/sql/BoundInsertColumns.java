package gumdrop.sql;

import gumdrop.common.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoundInsertColumns<T> {

  private final List<BoundInsertColumn<T, ?>> list = new ArrayList<>();

  public void add(BoundInsertColumn<T, ?> bc) {
    list.add(bc);
  }

  String getColSql() {
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

  void prepare(PreparedStatement ps, Logger logger) throws SQLException {
    for (BoundInsertColumn<T, ?> col : list) {
      logger.tok(String.valueOf(col.getU()));
      col.prepare(ps);
    }
  }

}
