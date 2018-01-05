package gumdrop.server;

import java.util.ArrayList;
import java.util.List;

public class FlexBuffer {

  private static final int BYTE_MASK = 0b1111_1111;

  private final List<byte[]> chunks = new ArrayList<>();

  private int size = 0;

  void put(byte[] bytes) {
    size += bytes.length;
    chunks.add(bytes);
  }

  void putInt(int n) {
    byte[] chunk = new byte[4];
    for (int i = Integer.BYTES - 1; i >= 0; i--) {
      byte b = (byte) (BYTE_MASK & n);
      chunk[i] = b;
      n >>= Byte.SIZE;
    }
    put(chunk);
  }

  void put(byte b) {
    put(new byte[]{b});
  }

  byte[] array() {
    byte[] out = new byte[size];
    int pos = 0;
    for (byte[] chunk : chunks) {
      System.arraycopy(chunk, 0, out, pos, chunk.length);
      pos += chunk.length;
    }
    return out;
  }

}
