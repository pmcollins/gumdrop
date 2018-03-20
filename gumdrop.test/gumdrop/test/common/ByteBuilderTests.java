package gumdrop.test.common;

import gumdrop.common.builder.ByteBuilder;
import gumdrop.test.util.Test;

import static gumdrop.test.util.Asserts.assertEquals;

public class ByteBuilderTests extends Test {

  public static void main(String[] args) {
    new ByteBuilderTests().run();
  }

  @Override
  public void run() {
    one();
    two();
  }

  private void one() {
    ByteBuilder bb = new ByteBuilder();
    byte[] hello = "hello".getBytes();
    bb.append(hello);
    int length = bb.length();
    assertEquals(length, hello.length);
    assertEquals('h', bb.charAt(0));
    assertEquals('o', bb.charAt(4));
  }

  private void two() {
    ByteBuilder bb = new ByteBuilder();
    bb.append("he".getBytes());
    bb.append("llo".getBytes());
    assertEquals(bb.length(), "hello".length());
    assertEquals('h', bb.charAt(0));
    assertEquals('o', bb.charAt(4));
    String substring = bb.substring(0, 3);
    assertEquals("hel", substring);
  }

}
