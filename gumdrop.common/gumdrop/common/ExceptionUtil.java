package gumdrop.common;

import java.util.concurrent.Callable;

public final class ExceptionUtil {

  private ExceptionUtil() {}

  public interface XRunnable {

    void run() throws Exception;

  }

  public static void run(XRunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T get(Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
