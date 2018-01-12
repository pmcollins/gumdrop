package gumdrop.test.util;

import gumdrop.common.ExceptionUtil;

public abstract class TestHarness implements Runnable {

  public TestHarness() {
    System.out.println(getClass().getName());
  }

  protected void test(ExceptionUtil.XRunnable runnable) {
    ExceptionUtil.run(runnable);
    System.out.println();
  }

}
