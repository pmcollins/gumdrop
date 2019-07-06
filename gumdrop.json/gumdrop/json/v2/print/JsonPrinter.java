package gumdrop.json.v2.print;

public abstract class JsonPrinter<T> {

  public String print(T t) {
    StringBuilder sb = new StringBuilder();
    print(sb, t);
    return sb.toString();
  }

  protected abstract void print(StringBuilder sb, T t);

}
