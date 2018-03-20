package gumdrop.test.common;

import gumdrop.test.util.TestSuite;

public class CommonTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new CommonTests().run();
  }

  @Override
  public void run() throws Exception {
    test(
      new ByteBuilderTests(),
      new ByteIteratorTests(),
      new BuilderTests()
    );
  }

}
