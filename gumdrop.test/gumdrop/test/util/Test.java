package gumdrop.test.util;

public abstract class Test implements Runnable {

  public Test() {
    System.out.print("\n" + getClass().getName() + '\n');
  }

}
