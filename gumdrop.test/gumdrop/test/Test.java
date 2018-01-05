package gumdrop.test;

public abstract class Test implements Runnable {

  public Test() {
    System.out.print("\n" + getClass().getName() + '\n');
  }

}
