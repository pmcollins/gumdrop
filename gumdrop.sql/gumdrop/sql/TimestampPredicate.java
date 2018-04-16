package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampPredicate extends Predicate<Timestamp> {

  public TimestampPredicate(String sql, Timestamp timestamp) {
    super(sql, timestamp);
  }

  @Override
  public int bind(PreparedStatement ps, int idx) throws SQLException {
    ps.setTimestamp(idx++, getT());
    return idx;
  }

}
