package gumdrop.test.util;

import java.util.List;
import java.util.Objects;

public class Asserts {

  public static void assertNotNull(Object o) {
    print();
    if (o == null) {
      throw new FailedTestException("assertNotNull failed");
    }
  }

  public static void assertNull(Object o) {
    print();
    if (o != null) {
      throw new FailedTestException("assertNull failed");
    }
  }

  public static void assertTrue(boolean condition) {
    print();
    if (!condition) {
      throw new FailedTestException("assertTrue condition not met");
    }
  }

  public static void assertFalse(boolean condition) {
    print();
    if (condition) {
      throw new FailedTestException("assertFalse condition not met");
    }
  }

  public static void assertEquals(Object expected, Object actual) {
    print();
    if (!expected.equals(actual)) {
      throw new FailedTestException("assertEquals expected [" + expected + "], got [" + actual + "]");
    }
  }

  public static <T> void assertListEquals(List<T> l1, List<T> l2) {
    int s1 = l1.size();
    int s2 = l2.size();
    if (s1 != s2) {
      throw new FailedTestException("list lengths not equal: [" + s1 + "] vs [" + s2 + "]");
    }
    for (int i = 0; i < s1; i++) {
      T t1 = l1.get(i);
      T t2 = l2.get(i);
      if (!Objects.equals(t1, t2)) {
        throw new FailedTestException("items not equal: [" + t1 + "] vs [" + t2 + "]");
      }
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
      throw new FailedTestException("assertThrows expected exception [" + klass + "], got [" + caught + "]");
    }
  }

  private static void print() {
    //System.out.print(".");
  }

}
