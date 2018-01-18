package gumdrop.test.util;

public class TestUtil {

  private TestUtil() { }

  public static String randomString(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append((char) TestUtil.randomInt('a', 'z'));
    }
    return sb.toString();
  }

  public static int randomInt(int min, int max) {
    return randomInt(Math.random(), min, max);
  }

  private static int randomInt(double rand, int min, int max) {
    return (int) Math.round(rand * (max - min)) + min;
  }

}
