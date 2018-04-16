package gumdrop.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public abstract class SelectColumn<T, U> {

  private final String name;
  private final BiConsumer<T, U> setter;

  SelectColumn(String name, BiConsumer<T, U> setter) {
    this.name = name;
    this.setter = setter;
  }

  public String getName() {
    return name;
  }

  void set(T t, U u) {
    setter.accept(t, u);
  }

  abstract public void set(ResultSet rs, T t, int idx) throws SQLException;

}
