package gumdrop.sql;

import gumdrop.common.Logger;
import gumdrop.common.StdoutLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Updater {

  private final String table;
  private final Logger logger;
  private Predicate<?>[] set;
  private Predicate<?>[] where;

  public Updater(String table) {
    this(table, new StdoutLogger("Updater"));
  }

  public Updater(String table, Logger logger) {
    this.table = table;
    this.logger = logger;
  }

  public void setSetClause(Predicate<?>... set) {
    this.set = set;
  }

  public void setWhereClause(Predicate<?>... where) {
    this.where = where;
  }

  public void execute(Connection connection) throws SQLException {
    String sql = buildSql();
    logger.line(sql);
    PreparedStatement ps = connection.prepareStatement(sql);
    int i = 1;
    for (Predicate<?> p : set) {
      log(p);
      i = p.bind(ps, i);
    }
    for (Predicate<?> p : where) {
      log(p);
      i = p.bind(ps, i);
    }
    logger.line();
    ps.executeUpdate();
  }

  private void log(Predicate<?> p) {
    logger.tok(p.getT());
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

}
