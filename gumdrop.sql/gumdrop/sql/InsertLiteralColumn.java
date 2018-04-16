package gumdrop.sql;

import java.sql.PreparedStatement;

public class InsertLiteralColumn<T> extends InsertColumn<T, String> {

  private final String sql;

  public InsertLiteralColumn(String name, String sql) {
    super(name, null);
    this.sql = sql;
  }

  @Override
  public void set(PreparedStatement ps, int idx, String foo) {
  }

  @Override
  public void appendBind(StringBuilder bind) {
    bind.append(sql);
  }

  @Override
  String applyGetter(T t) {
    return sql;
  }

}
