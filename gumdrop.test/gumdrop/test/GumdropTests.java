package gumdrop.test;

import gumdrop.test.common.CommonTests;
import gumdrop.test.json.JsonTests;
import gumdrop.test.util.TestSuite;

public class GumdropTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new GumdropTests().run();
  }

  @Override
  public void run() throws Exception {
    test(new CommonTests(), new JsonTests());
  }

}
