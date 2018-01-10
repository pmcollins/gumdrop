package gumdrop.common;

public final class ExceptionUtil {

  private ExceptionUtil() {
  }

  public interface XRunnable {

    void run() throws Exception;

  }

  public interface XSupplier<T> {

    T get() throws Exception;

  }

  public static void run(XRunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T get(XSupplier<T> XSupplier) {
    try {
      return XSupplier.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
