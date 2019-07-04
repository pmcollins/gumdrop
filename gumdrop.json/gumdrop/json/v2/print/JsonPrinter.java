package gumdrop.json.v2.print;

public interface JsonPrinter<T> {
  void print(StringBuilder sb, T t);
}
