package gumdrop.sql;

import revlab.core.entities.Entity;

import java.sql.*;

public class Inserter<T extends Entity> {

  private final String tableName;
  private final InsertColumnCollection<T> columns;

  public Inserter(String tableName, InsertColumnCollection<T> columns) {
    this.tableName = tableName;
    this.columns = columns;
  }

  public String insert(Connection connection, T t) throws SQLException {
    BoundInsertColumns<T> boundSubset = columns.getBoundSubset(t);
    String sql = "INSERT INTO " + tableName + " " + boundSubset.getColSql();
    System.out.println(sql);
    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    boundSubset.prepare(ps);
    ps.executeUpdate();
    ResultSet generatedKeys = ps.getGeneratedKeys();
    generatedKeys.next();
    t.setId(generatedKeys.getInt(1));
    return sql;
  }

}
