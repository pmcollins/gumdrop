package gumdrop.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public class SelectIntegerColumn<T> extends SelectColumn<T, Integer> {

  public SelectIntegerColumn(String name, BiConsumer<T, Integer> setter) {
    super(name, setter);
  }

  @Override
  public void set(ResultSet rs, T t, int idx) throws SQLException {
    int anInt = rs.getInt(idx);
    if (!rs.wasNull()) {
      set(t, anInt);
    }
  }

}
