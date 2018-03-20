package gumdrop.test.server;

import gumdrop.common.builder.ByteIterator;
import gumdrop.web.Delimiter;
import gumdrop.test.util.Test;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertFalse;
import static gumdrop.test.util.Asserts.assertTrue;

public class DelimiterTest extends Test {

  public static void main(String[] args) {
    new DelimiterTest().run();
  }

  @Override
  public void run() {
    fwdDelimiterTest();
    fwdDelimiterTest2();
    fwdDelimiterTest3();
  }

  private void fwdDelimiterTest() {
    Delimiter delim = new Delimiter("cde");
    ByteIterator it = new ByteIterator("abcdefg");
    assertFalse(delim.match(it));
    it.position(2);
    assertEquals('c', it.currentChar());
    assertTrue(delim.match(it));
    assertEquals('c', it.currentChar());
  }

  private void fwdDelimiterTest2() {
    Delimiter d = new Delimiter("cdefghijk");
    ByteIterator it = new ByteIterator("abcdefg");
    assertFalse(d.match(it));
    it.position(2);
    assertEquals('c', it.currentChar());
    assertFalse(d.match(it));
    assertEquals('c', it.currentChar());
  }

  private void fwdDelimiterTest3() {
    Delimiter d = new Delimiter("ccc");
    ByteIterator it = new ByteIterator("abcdefg");
    assertFalse(d.match(it));
  }

}
