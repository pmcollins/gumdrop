package gumdrop.common;

public class StdoutLogger implements Logger {

  private final String name;

  public StdoutLogger(String name) {
    this.name = name;
  }

  @Override
  public void tok(Object tok) {
    System.out.print("[" + tok + "]");
  }

  @Override
  public void line(String line, String... args) {
    StringBuilder s = new StringBuilder(name + ": " + line);
    for (String arg : args) {
      s.append(" [").append(arg).append("]");
    }
    System.out.println(s);
  }

  @Override
  public void line() {
    System.out.println();
  }

}
