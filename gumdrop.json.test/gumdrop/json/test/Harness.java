package gumdrop.json.test;

import gumdrop.test.TestHarness;

class Harness extends TestHarness {

  public static void main(String[] args) {
    new Harness().run();
  }

  @Override
  public void run() {
    test(new BuilderDelegateTest());
    test(new CharIteratorTest());
    test(new GettersTest());
    test(new JsonBuilderTest());
    test(new JsonReaderTest());
    test(new ObjectBuilderTest());
  }

}
