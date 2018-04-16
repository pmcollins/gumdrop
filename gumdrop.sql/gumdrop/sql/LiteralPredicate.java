package gumdrop.sql;

import java.sql.PreparedStatement;

public class LiteralPredicate extends Predicate<Void> {

  public LiteralPredicate(String sql) {
    super(sql, null);
  }

  @Override
  public int bind(PreparedStatement ps, int idx) {
    return idx;
  }

}
