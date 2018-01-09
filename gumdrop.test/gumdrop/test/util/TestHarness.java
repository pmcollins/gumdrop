package gumdrop.test.util;

public abstract class TestHarness implements Runnable {

  public TestHarness() {
    System.out.println(getClass().getName());
  }

  protected void test(Runnable runnable) {
    runnable.run();
    System.out.println();
  }

}
