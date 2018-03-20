package gumdrop.test;

import gumdrop.test.common.CommonTests;
import gumdrop.test.json.JsonTests;
import gumdrop.test.server.ServerTests;
import gumdrop.test.util.TestSuite;
import gumdrop.test.web.WebTests;

public class GumdropTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new GumdropTests().run();
  }

  @Override
  public void run() throws Exception {
    test(
      new CommonTests(),
      new JsonTests(),
      new ServerTests(),
      new WebTests()
    );
  }

}
