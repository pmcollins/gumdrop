package gumdrop.sql;

import gumdrop.common.Entity;
import gumdrop.common.Logger;
import gumdrop.common.StdoutLogger;

import java.sql.*;

public class Inserter<T extends Entity> {

  private final String tableName;
  private final InsertColumnCollection<T> columns;
  private final Logger logger;

  public Inserter(String tableName, InsertColumnCollection<T> columns) {
    this(tableName, columns, new StdoutLogger(tableName + " inserter"));
  }

  public Inserter(String tableName, InsertColumnCollection<T> columns, Logger logger) {
    this.tableName = tableName;
    this.columns = columns;
    this.logger = logger;
  }

  public String insert(Connection connection, T t) throws SQLException {
    BoundInsertColumns<T> boundSubset = columns.getBoundSubset(t);
    String sql = "INSERT INTO " + tableName + " " + boundSubset.getColSql();
    logger.line(sql);
    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    boundSubset.prepare(ps, logger);
    logger.line();
    ps.executeUpdate();
    ResultSet generatedKeys = ps.getGeneratedKeys();
    generatedKeys.next();
    t.setId(generatedKeys.getInt(1));
    return sql;
  }

}
