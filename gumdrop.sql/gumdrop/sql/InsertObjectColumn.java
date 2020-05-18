package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class InsertObjectColumn<T> extends InsertColumn<T, Object> {

  public InsertObjectColumn(String name, Function<T, Object> getter) {
    super(name, getter);
  }

  @Override
  public void set(PreparedStatement ps, int idx, Object o) throws SQLException {
    ps.setObject(idx, o);
  }

}
