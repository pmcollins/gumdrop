package gumdrop.test.common;

import gumdrop.common.CharIterator;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import static gumdrop.test.util.Asserts.*;

class CharIteratorTest extends Test {

  public static void main(String[] args) {
    new CharIteratorTest().run();
  }

  @Override
  public void run() {
    a();
    b();
  }

  private void a() {
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
    Asserts.assertThrows(it::next, IllegalStateException.class);
    it.append("ghijk");
    assertEquals('g', it.current());
    it.increment();
    assertEquals("fg", it.substring());
  }

  private void b() {
    CharIterator it = new CharIterator("abcdef");
    int pos = it.position();
    assertEquals(0, pos);
    it.last();
    assertEquals('f', it.current());
    assertEquals(5, it.position());
    it.position(1);
    assertEquals('b', it.current());
    it.decrement();
    assertEquals('a', it.current());
    assertThrows(it::decrement, IllegalStateException.class);
  }

}
