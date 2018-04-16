package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class PasswordColumn<T> extends InsertColumn<T, String> {

  public PasswordColumn(String name, Function<T, String> getter) {
    super(name, getter);
  }

  @Override
  public void set(PreparedStatement ps, int idx, String s) throws SQLException {
    ps.setString(idx, s);
  }

  @Override
  public void appendBind(StringBuilder bind) {
    bind.append("crypt(?, gen_salt('bf'))");
  }

}
