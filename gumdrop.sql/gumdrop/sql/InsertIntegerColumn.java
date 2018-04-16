package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class InsertIntegerColumn<T> extends InsertColumn<T, Integer> {

  public InsertIntegerColumn(String name, Function<T, Integer> getter) {
    super(name, getter);
  }

  @Override
  public void set(PreparedStatement ps, int idx, Integer integer) throws SQLException {
    ps.setInt(idx, integer);
  }

}
