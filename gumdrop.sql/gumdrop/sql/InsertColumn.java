package gumdrop.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public abstract class InsertColumn<T, U> {

  private final String name;
  private final Function<T, U> getter;

  InsertColumn(String name, Function<T, U> getter) {
    this.name = name;
    this.getter = getter;
  }

  public abstract void set(PreparedStatement ps, int idx, U u) throws SQLException;

  public String getName() {
    return name;
  }

  U applyGetter(T t) {
    return getter.apply(t);
  }

  public void appendBind(StringBuilder bind) {
    bind.append('?');
  }

}
