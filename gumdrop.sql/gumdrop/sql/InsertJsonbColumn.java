package gumdrop.sql;

import java.util.function.Function;

public class InsertJsonbColumn<T> extends InsertObjectColumn<T> {

  public InsertJsonbColumn(String name, Function<T, Object> getter) {
    super(name, getter);
  }

  @Override
  public void appendBind(StringBuilder bind) {
    bind.append("to_jsonb(?::jsonb)");
  }

}
