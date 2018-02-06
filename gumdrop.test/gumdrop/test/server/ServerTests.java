package gumdrop.test.server;

import gumdrop.test.util.TestSuite;

public class ServerTests extends TestSuite {

  public static void main(String[] args) throws Exception {
    new ServerTests().run();
  }

  @Override
  public void run() throws Exception {
    test(
      new AttributeAccumulatorTest(),
      new DelimiterTest(),
      new InterruptibleRequestParserTest(),
      new MultiParserTest(),
      new QueueTest(),
      new WordAccumulatorTest()
    );
  }

}
