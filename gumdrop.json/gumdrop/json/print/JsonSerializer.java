package gumdrop.json.print;

public abstract class JsonSerializer<T> {

  public final String toJson(T t) {
    StringBuilder sb = new StringBuilder();
    toJson(t, sb);
    return sb.toString();
  }

  public final void toJson(T t, StringBuilder sb) {
    if (t == null) {
      sb.append("null");
    } else {
      nonNullToJson(t, sb);
    }
  }

  protected abstract void nonNullToJson(T t, StringBuilder sb);

}
