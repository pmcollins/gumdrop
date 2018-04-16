package gumdrop.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public class SelectStringColumn<T> extends SelectColumn<T, String> {

  public SelectStringColumn(String name, BiConsumer<T, String> setter) {
    super(name, setter);
  }

  @Override
  public void set(ResultSet rs, T t, int idx) throws SQLException {
    set(t, rs.getString(idx));
  }

}
