package gumdrop.test.common;

import gumdrop.common.CharIterator;
import gumdrop.test.util.Test;
import gumdrop.test.util.TestUtil;

import static gumdrop.test.util.TestUtil.assertEquals;
import static gumdrop.test.util.TestUtil.assertFalse;
import static gumdrop.test.util.TestUtil.assertTrue;

class CharIteratorTest extends Test {

  public static void main(String[] args) {
    new CharIteratorTest().run();
  }

  @Override
  public void run() {
    CharIterator it = new CharIterator("abcdef");
    assertEquals('a', it.current());
    assertEquals('a', it.bump());
    assertEquals('b', it.current());
    assertEquals("a", it.substring());
    it.mark();
    it.increment();
    it.increment();
    assertEquals('d', it.current());
    assertEquals("bc", it.substring());
    assertEquals('e', it.next());
    it.increment();
    assertEquals('f', it.current());
    it.mark();
    assertFalse(it.done());
    assertEquals(CharIterator.DONE, it.next());
    assertEquals(CharIterator.DONE, it.current());
    assertTrue(it.done());
    TestUtil.assertThrows(it::next, IllegalStateException.class);
    it.append("ghijk");
    assertEquals('g', it.current());
    it.increment();
    assertEquals("fg", it.substring());
  }

}
