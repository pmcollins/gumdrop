package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.common.StringUtil;

import java.util.Collections;
import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;

public class StringUtilTest extends Test {

  private enum Foo {
    ONE,
    TWO_THREE,
    A_B
  }

  public static void main(String[] args) {
    new StringUtilTest().run();
  }

  @Override
  public void run() {
    testToTitleCase();
    testParseQueryString();
  }

  private static void testToTitleCase() {
    assertEquals("One", StringUtil.toTitleCase(Foo.ONE));
    assertEquals("Two Three", StringUtil.toTitleCase(Foo.TWO_THREE));
    assertEquals("A B", StringUtil.toTitleCase(Foo.A_B));
  }

  private static void testParseQueryString() {
    assertEquals(Map.of("a", "b"), StringUtil.parseQueryString("a=b"));
    assertEquals(Map.of("a", "b", "c", "d"), StringUtil.parseQueryString("a=b&c=d"));
    assertEquals(Map.of(), StringUtil.parseQueryString("hello"));
    assertEquals(Map.of("a", "b", "c", "d"), StringUtil.parseQueryString("a=b&c=d&foo"));
  }

}
