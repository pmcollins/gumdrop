package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.Function;

public class InsertTimestampColumn<T> extends InsertColumn<T, Timestamp> {

  public InsertTimestampColumn(String name, Function<T, Timestamp> getter) {
    super(name, getter);
  }

  @Override
  public void set(PreparedStatement ps, int idx, Timestamp timestamp) throws SQLException {
    ps.setTimestamp(idx, timestamp);
  }

}
