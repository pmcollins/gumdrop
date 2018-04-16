package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class InsertBooleanColumn<T> extends InsertColumn<T, Boolean> {

  public InsertBooleanColumn(String name, Function<T, Boolean> getter) {
    super(name, getter);
  }

  @Override
  public void set(PreparedStatement ps, int idx, Boolean aBoolean) throws SQLException {
    ps.setBoolean(idx, aBoolean);
  }

}
