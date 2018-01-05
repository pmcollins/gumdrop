package gumdrop.json.test;

import gumdrop.json.CharIterator;
import gumdrop.test.Test;
import gumdrop.test.TestUtil;

public class CharIteratorTest extends Test {

  public static void main(String[] args) {
    new CharIteratorTest().run();
  }

  @Override
  public void run() {
    CharIterator it = new CharIterator("abcdef");
    TestUtil.assertEquals('a', it.current());
    TestUtil.assertEquals('a', it.bump());
    TestUtil.assertEquals('b', it.current());
    it.mark();
    it.increment();
    it.increment();
    TestUtil.assertEquals('d', it.current());
    TestUtil.assertEquals("bc", it.substring());
    TestUtil.assertEquals('e', it.next());
    it.increment();
    TestUtil.assertEquals(CharIterator.DONE, it.next());
    TestUtil.assertEquals(CharIterator.DONE, it.current());
    TestUtil.assertThrows(it::next, IllegalStateException.class);
  }

}
