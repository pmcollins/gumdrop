package gumdrop.json2;

public interface JsonDelegate {

  void push(String string);

  void pop(String string);

  void push();

  void pop();

}
