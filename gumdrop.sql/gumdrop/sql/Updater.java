package gumdrop.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Updater {

  private final String table;
  private Predicate<?>[] set;
  private Predicate<?>[] where;

  public Updater(String table) {
    this.table = table;
  }

  public void setSetClause(Predicate<?>... set) {
    this.set = set;
  }

  public void setWhereClause(Predicate<?>... where) {
    this.where = where;
  }

  public String buildSql() {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE ").append(table).append(" SET ");
    for (int i = 0; i < set.length; i++) {
      if (i > 0) sb.append(',');
      sb.append(set[i].getSql());
    }
    sb.append(" WHERE ");
    for (int i = 0; i < where.length; i++) {
      if (i > 0) sb.append(" AND ");
      sb.append(where[i].getSql());
    }
    return sb.toString();
  }

  public void execute(Connection connection) throws SQLException {
    PreparedStatement ps = connection.prepareStatement(buildSql());
    int i = 1;
    for (Predicate<?> p : set) {
      i = p.bind(ps, i);
    }
    for (Predicate<?> p : where) {
      i = p.bind(ps, i);
    }
    ps.executeUpdate();
  }

}
