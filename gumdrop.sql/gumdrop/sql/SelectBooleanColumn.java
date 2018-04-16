package gumdrop.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public class SelectBooleanColumn<T> extends SelectColumn<T, Boolean> {

  public SelectBooleanColumn(String name, BiConsumer<T, Boolean> setter) {
    super(name, setter);
  }

  @Override
  public void set(ResultSet rs, T t, int idx) throws SQLException {
    set(t, rs.getBoolean(idx));
  }

}
