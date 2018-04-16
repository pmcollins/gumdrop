package gumdrop.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.BiConsumer;

public class SelectTimestampColumn<T> extends SelectColumn<T, Timestamp> {

  public SelectTimestampColumn(String name, BiConsumer<T, Timestamp> setter) {
    super(name, setter);
  }

  @Override
  public void set(ResultSet rs, T t, int idx) throws SQLException {
    set(t, rs.getTimestamp(idx));
  }

}
