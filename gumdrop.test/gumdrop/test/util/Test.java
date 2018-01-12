package gumdrop.test.util;

import gumdrop.common.ExceptionUtil;

public abstract class Test implements ExceptionUtil.XRunnable {

  public Test() {
    System.out.print("\n" + getClass().getName() + '\n');
  }

}
