package gumdrop.test.json.v2;

interface JsonPrinter<T> {
  void print(StringBuilder sb, T t);
}
