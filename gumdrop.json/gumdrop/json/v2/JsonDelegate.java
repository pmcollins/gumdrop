package gumdrop.json.v2;

public interface JsonDelegate {

  void push(String string);

  void pop(String string);

  void accept(String val);

  void push();

  void pop();

  void nullValue();

}
