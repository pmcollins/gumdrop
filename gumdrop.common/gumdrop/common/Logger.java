package gumdrop.common;

public interface Logger {

  void tok(Object tok);

  void line(String line, String... args);

  void line();

}
