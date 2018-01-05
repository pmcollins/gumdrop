package gumdrop.test;

public class PerfUtil {

  public static int time(Runnable runnable, int iterations) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < iterations; i++) {
      runnable.run();
    }
    long end = System.currentTimeMillis();
    return (int) (end - start);
  }

}
