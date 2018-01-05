package gumdrop.test;

public class TestUtil {

  public static void assertNotNull(Object o) {
    print();
    if (o == null) {
      throw new FailedTestException("assertNotNull failed");
    }
  }

  public static void assertTrue(boolean condition) {
    print();
    if (!condition) {
      throw new FailedTestException("condition not met");
    }
  }

  public static void assertEquals(Object expected, Object actual) {
    print();
    if (!expected.equals(actual)) {
      throw new FailedTestException("expected [" + expected + "], got [" + actual + "]");
    }
  }

  public static void assertThrows(Runnable r, Class klass) {
    print();
    Throwable caught = null;
    try {
      r.run();
    } catch (Throwable e) {
      caught = e;
    }
    if (caught == null || !caught.getClass().equals(klass)) {
      throw new FailedTestException("expected exception [" + klass + "], got [" + caught + "]");
    }
  }

  private static void print() {
    System.out.print(".");
  }

}
