package gumdrop.json.v2;

public interface JsonDelegate {

  void push(String string);

  void pop(String string);

  void acceptString(String val);

  void acceptBareword(String bareword);

  void push();

  void pop();

}
