package gumdrop.sql;

import gumdrop.common.Logger;
import gumdrop.common.StdoutLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Selector<T> {

  private final String fromClause;
  private final SelectColumns<T> columns;
  private final Logger logger;

  public Selector(String fromClause, SelectColumns<T> columns) {
    this(fromClause, columns, new StdoutLogger("Selector"));
  }

  public Selector(String fromClause, SelectColumns<T> columns, Logger logger) {
    this.fromClause = fromClause;
    this.columns = columns;
    this.logger = logger;
  }

  public Optional<T> selectFirst(Connection connection, Predicate<?>... predicates) throws SQLException {
    ResultSet rs = getResultSet(connection, predicates);
    if (rs.next()) {
      T t = buildInstance(rs);
      return Optional.of(t);
    } else {
      return Optional.empty();
    }
  }

  public List<T> select(Connection connection, Predicate<?>... predicates) throws SQLException {
    ResultSet rs = getResultSet(connection, predicates);
    List<T> out = new ArrayList<>();
    while (rs.next()) {
      T t = buildInstance(rs);
      out.add(t);
    }
    return out;
  }

  private ResultSet getResultSet(Connection connection, Predicate<?>[] predicates) throws SQLException {
    String sql = getSql(predicates);
    logger.line(sql);
    PreparedStatement ps = connection.prepareStatement(sql);
    int i = 1;
    for (Predicate<?> predicate : predicates) {
      logger.tok(predicate.getT());
      predicate.bind(ps, i++);
    }
    logger.line();
    return ps.executeQuery();
  }

  private T buildInstance(ResultSet rs) throws SQLException {
    T t = columns.createInstance();
    int j = 1;
    for (SelectColumn<T, ?> column : columns) {
      column.set(rs, t, j++);
    }
    return t;
  }

  public String getSql(Predicate<?>... predicates) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (SelectColumn<T, ?> column : columns) {
      if (i++ > 0) {
        sb.append(',');
      }
      sb.append(column.getName());
    }
    StringBuilder sql = new StringBuilder("SELECT " + sb + " FROM " + fromClause);
    if (predicates != null && predicates.length > 0) {
      sql.append(" WHERE ");
      int j = 0;
      for (Predicate<?> predicate : predicates) {
        if (j++ > 0) {
          sql.append(" AND ");
        }
        sql.append(predicate.getSql());
      }
    }
    return sql.toString();
  }

}
