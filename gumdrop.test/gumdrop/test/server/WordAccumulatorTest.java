package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.web.WordAccumulator;
import gumdrop.test.util.Test;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertFalse;
import static gumdrop.test.util.Asserts.assertTrue;

public class WordAccumulatorTest extends Test {

  public static void main(String[] args) {
    new WordAccumulatorTest().run();
  }

  @Override
  public void run() {
    wordAccumulator();
    wordAccumulator2();
    wordAccumulator3();
  }

  private void wordAccumulator() {
    WordAccumulator accumulator = new WordAccumulator("\r\n");
    assertTrue(accumulator.match(new CharIterator("XXX\r\nZZZ")));
    String val = accumulator.getVal();
    assertEquals("XXX", val);
    CharIterator it = new CharIterator("XXX\rYYY\r\nZZZ");
    assertTrue(accumulator.match(it));
    assertEquals("XXX\rYYY", accumulator.getVal());
    assertEquals('\r', it.current());
  }

  private void wordAccumulator2() {
    WordAccumulator accumulator = new WordAccumulator("\r\n");
    CharIterator it = new CharIterator("XXX\r");
    assertFalse(accumulator.match(it));
    it.append("\n");
    assertTrue(accumulator.match(it));
    assertEquals("XXX", accumulator.getVal());
  }

  private void wordAccumulator3() {
    WordAccumulator accumulator = new WordAccumulator("Hello");
    CharIterator it = new CharIterator("XXXHell");
    assertFalse(accumulator.match(it));
    it.append("xYYYHello");
    assertTrue(accumulator.match(it));
    assertEquals("XXXHellxYYY", accumulator.getVal());
  }

}
