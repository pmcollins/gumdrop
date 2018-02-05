package gumdrop.test.server;

import gumdrop.common.CharIterator;
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
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(delim.match(it));
    it.position(2);
    assertEquals('c', it.current());
    assertTrue(delim.match(it));
    assertEquals('c', it.current());
  }

  private void fwdDelimiterTest2() {
    Delimiter d = new Delimiter("cdefghijk");
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(d.match(it));
    it.position(2);
    assertEquals('c', it.current());
    assertFalse(d.match(it));
    assertEquals('c', it.current());
  }

  private void fwdDelimiterTest3() {
    Delimiter d = new Delimiter("ccc");
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(d.match(it));
  }

}
