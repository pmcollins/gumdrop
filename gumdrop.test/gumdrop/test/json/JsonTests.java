package gumdrop.test.json;

import gumdrop.test.util.TestSuite;

public class JsonTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new JsonTests().run();
  }

  @Override
  public void run() throws Exception {
    test(new BuilderDelegateTest(), new GettersTest(), new JsonBuilderTest(), new JsonReaderTest());
  }

}
