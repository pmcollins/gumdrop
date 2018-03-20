package gumdrop.test.web;

import gumdrop.test.util.TestSuite;

public class WebTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new WebTests().run();
  }

  @Override
  public void run() throws Exception {
    test(
      new HtmlStringUtilTest(),
      new HttpFormReaderTest(),
      new DispatcherTest(),
      new MethodDispatcherTest(),
      new PathBuilderTest(),
      new PatternTest(),
      new StaticControllerTest(),
      new StringUtilTest()
    );
  }

}
