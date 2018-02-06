package gumdrop.test.util;

public abstract class TestSuite extends Test {

  protected void test(Test... tests) throws Exception {
    for (Test test : tests) {
      test.run();
    }
  }

}
