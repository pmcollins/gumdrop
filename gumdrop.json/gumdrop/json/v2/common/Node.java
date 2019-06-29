package gumdrop.json.v2.common;

public interface Node {

  Node next();

  Node next(String key);

  void accept(String value);

}
