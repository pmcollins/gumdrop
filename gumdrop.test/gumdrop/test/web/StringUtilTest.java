package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.common.StringUtil;

import static gumdrop.test.util.Asserts.assertEquals;

public class StringUtilTest extends Test {

  public static void main(String[] args) {
    new StringUtilTest().run();
  }

  @Override
  public void run() {
    assertEquals("One", StringUtil.toTitleCase(Type.ONE));
    assertEquals("Two Three", StringUtil.toTitleCase(Type.TWO_THREE));
    assertEquals("A B", StringUtil.toTitleCase(Type.A_B));
  }

  private enum Type {
    ONE,
    TWO_THREE,
    A_B
  }

}
