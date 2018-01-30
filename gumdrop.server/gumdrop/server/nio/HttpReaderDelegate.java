package gumdrop.server.nio;

public interface HttpReaderDelegate {

  void word(String word);

  void key(String key);

  void value(String value);

  void post(String post);

}
