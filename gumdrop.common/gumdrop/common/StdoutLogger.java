package gumdrop.common;

public class StdoutLogger implements Logger {

  private final String name;

  public StdoutLogger(String name) {
    this.name = name;
  }

  @Override
  public void tok(String tok) {
    System.out.print("[" + tok + "]");
  }

  @Override
  public void line(String line) {
    System.out.println(name + ": " + line);
  }

  @Override
  public void line() {
    System.out.println();
  }

}
