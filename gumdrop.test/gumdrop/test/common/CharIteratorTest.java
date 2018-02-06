package gumdrop.test.common;

import gumdrop.common.ByteIterator;
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
    ByteIterator it = new ByteIterator("abcdef");
    assertEquals('a', it.currentChar());
    assertEquals('a', it.bumpChar());
    assertEquals('b', it.currentChar());
    assertEquals("a", it.substring());
    it.mark();
    it.increment();
    it.increment();
    assertEquals('d', it.currentChar());
    assertEquals("bc", it.substring());
    assertEquals('e', it.nextChar());
    it.increment();
    assertEquals('f', it.currentChar());
    it.mark();
    assertFalse(it.done());
    it.nextChar();
    assertTrue(it.done());
    Asserts.assertThrows(it::nextChar, IllegalStateException.class);
    it.append("ghijk");
    assertEquals('g', it.currentChar());
    it.increment();
    assertEquals("fg", it.substring());
  }

  private void b() {
    ByteIterator it = new ByteIterator("abcdef");
    int pos = it.position();
    assertEquals(0, pos);
    it.positionLast();
    assertEquals('f', it.currentChar());
    assertEquals(5, it.position());
    it.position(1);
    assertEquals('b', it.currentChar());
    it.decrement();
    assertEquals('a', it.currentChar());
    assertThrows(it::decrement, IllegalStateException.class);
  }

}
