package gumdrop.server.test;

import gumdrop.server.FlexBuffer;
import gumdrop.test.Test;
import gumdrop.test.TestUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class FlexBufferTest extends Test {

  public static void main(String[] args) {
    FlexBufferTest test = new FlexBufferTest();
    test.run();
  }

  @Override
  public void run() {
    simple();
    multi();
    putByte();
    putIntRightBit();
    putIntLeftBit();
    putInt();
    putByte();
  }

  private void simple() {
    FlexBuffer fb = new FlexBuffer();
    byte[] a = {1, 2, 3};
    fb.put(a);
    TestUtil.assertTrue(Arrays.equals(a, fb.array()));
  }

  private void multi() {
    FlexBuffer fb = new FlexBuffer();
    fb.put(new byte[]{1, 2, 3});
    fb.put(new byte[]{4, 5});
    TestUtil.assertTrue(Arrays.equals(new byte[]{1, 2, 3, 4, 5}, fb.array()));
  }

  private void putIntRightBit() {
    assertFbRoundTrip(1);
  }

  private void putIntLeftBit() {
    assertFbRoundTrip(0b1000_0000_0000_0000);
  }

  private void putInt() {
    assertFbRoundTrip(42);
  }

  private void putByte() {
    FlexBuffer fb = new FlexBuffer();
    byte n = (byte) 42;
    fb.put(n);
    ByteBuffer bb = ByteBuffer.wrap(fb.array());
    byte retrieved = bb.get();
    TestUtil.assertEquals(n, retrieved);
  }

  private static void assertFbRoundTrip(int n) {
    FlexBuffer fb = new FlexBuffer();
    fb.putInt(n);
    ByteBuffer bb = ByteBuffer.wrap(fb.array());
    int bbInt = bb.getInt();
    TestUtil.assertEquals(n, bbInt);
  }

}
