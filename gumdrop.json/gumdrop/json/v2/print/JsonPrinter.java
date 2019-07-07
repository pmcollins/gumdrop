package gumdrop.json.v2.print;

public abstract class JsonPrinter<T> {

  public String print(T t) {
    StringBuilder sb = new StringBuilder();
    print(sb, t);
    return sb.toString();
  }

  public final void print(StringBuilder sb, T t) {
    if (t == null) {
      sb.append("null");
    } else {
      printNonNull(sb, t);
    }
  }

  protected abstract void printNonNull(StringBuilder sb, T t);

}
