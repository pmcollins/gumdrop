package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntegerPredicate extends Predicate<Integer> {

  public IntegerPredicate(String sql, Integer integer) {
    super(sql, integer);
  }

  @Override
  public int bind(PreparedStatement ps, int idx) throws SQLException {
    ps.setInt(idx++, getT());
    return idx;
  }

}
