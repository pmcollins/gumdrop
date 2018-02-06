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
    StringBuilder sb = new StringBuilder();
    for (int i = start; i < end; i++) {
      sb.append(charAt(i));
    }
    return sb.toString();
  }

  public char charAt(int idx) {
    for (byte[] chunk : chunks) {
      if (idx < chunk.length) {
        return (char) chunk[idx];
      } else {
        idx -= chunk.length;
      }
    }
    return 0;
  }

}
