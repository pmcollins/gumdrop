package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.common.StringUtil;

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
    assertEquals("One", StringUtil.toTitleCase(Foo.ONE));
    assertEquals("Two Three", StringUtil.toTitleCase(Foo.TWO_THREE));
    assertEquals("A B", StringUtil.toTitleCase(Foo.A_B));
  }

}
