package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringPredicate extends Predicate<String> {

  public StringPredicate(String sql, String value) {
    super(sql, value);
  }

  @Override
  public int bind(PreparedStatement ps, int idx) throws SQLException {
    ps.setString(idx++, getT());
    return idx;
  }

}
