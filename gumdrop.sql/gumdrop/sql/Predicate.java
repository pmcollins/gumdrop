package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Predicate<T> {

  private final String sql;
  private final T t;

  Predicate(String sql, T t) {
    this.sql = sql;
    this.t = t;
  }

  public String getSql() {
    return sql;
  }

  T getT() {
    return t;
  }

  public abstract int bind(PreparedStatement ps, int idx) throws SQLException;

}
