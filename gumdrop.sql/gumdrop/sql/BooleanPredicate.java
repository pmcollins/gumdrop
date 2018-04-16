package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooleanPredicate extends Predicate<Boolean> {

  public BooleanPredicate(String sql, Boolean aBoolean) {
    super(sql, aBoolean);
  }

  @Override
  public int bind(PreparedStatement ps, int idx) throws SQLException {
    ps.setBoolean(idx++, getT());
    return idx;
  }

}
