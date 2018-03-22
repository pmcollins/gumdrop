package gumdrop.common;

import java.util.ArrayList;
import java.util.List;

public class ByteBuilder {

  private final List<byte[]> chunks = new ArrayList<>();
  private int length;

  public void append(byte[] bytes) {
    chunks.add(bytes);
    length += bytes.length;
  }

  public int length() {
    return length;
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
    // one chunk will be the norm, two rare, three+ supported but not expected
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
