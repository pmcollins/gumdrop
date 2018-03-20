package gumdrop.test.server;

import gumdrop.common.builder.ByteIterator;
import gumdrop.web.http.WordAccumulator;
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
    assertTrue(accumulator.match(new ByteIterator("XXX\r\nZZZ")));
    String val = accumulator.getSubstring();
    assertEquals("XXX", val);
    ByteIterator it = new ByteIterator("XXX\rYYY\r\nZZZ");
    assertTrue(accumulator.match(it));
    assertEquals("XXX\rYYY", accumulator.getSubstring());
    assertEquals('\r', it.currentChar());
  }

  private void wordAccumulator2() {
    WordAccumulator accumulator = new WordAccumulator("\r\n");
    ByteIterator it = new ByteIterator("XXX\r");
    assertFalse(accumulator.match(it));
    it.append("\n");
    assertTrue(accumulator.match(it));
    assertEquals("XXX", accumulator.getSubstring());
  }

  private void wordAccumulator3() {
    WordAccumulator accumulator = new WordAccumulator("Hello");
    ByteIterator it = new ByteIterator("XXXHell");
    assertFalse(accumulator.match(it));
    it.append("xYYYHello");
    assertTrue(accumulator.match(it));
    assertEquals("XXXHellxYYY", accumulator.getSubstring());
  }

}
