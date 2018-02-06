package gumdrop.common;

import java.util.ArrayList;
import java.util.List;

public class ByteBuilder {

  private final List<byte[]> chunks = new ArrayList<>();

  public void append(byte[] bytes) {
    chunks.add(bytes);
  }

  public int length() {
    int out = 0;
    for (byte[] chunk : chunks) {
      out += chunk.length;
    }
    return out;
  }

  public String substring(int start, int end) {
    return new String(subarray(start, end));
  }

  public byte[] subarray(int start, int end) {
    byte[] out = new byte[end - start];
    int idx = 0;
    for (int i = start; i < end; i++) {
      out[idx++] = byteAt(i);
    }
    return out;
  }

  public char charAt(int idx) {
    return (char) byteAt(idx);
  }

  public byte byteAt(int idx) {
    for (byte[] chunk : chunks) {
      if (idx < chunk.length) {
        return chunk[idx];
      } else {
        idx -= chunk.length;
      }
    }
    return 0;
  }

}
