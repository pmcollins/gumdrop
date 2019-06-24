package gumdrop.test.json.v1;

import gumdrop.test.util.TestSuite;

public class JsonTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new JsonTests().run();
  }

  @Override
  public void run() throws Exception {
    test(
      new BuilderDelegateTest(),
      new JsonWriterTest(),
      new JsonConverterTest(),
      new JsonReaderTest()
    );
  }

}
