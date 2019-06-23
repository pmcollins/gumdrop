package gumdrop.json2;

public interface BuilderNode {
  BuilderNode next();
  BuilderNode next(String key);
  void accept(String value);
}
