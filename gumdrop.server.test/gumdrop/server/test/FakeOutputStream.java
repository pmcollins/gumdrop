package gumdrop.server.test;

import java.io.OutputStream;

class FakeOutputStream extends OutputStream {

  private final StringBuilder sb = new StringBuilder();

  @Override
  public void write(int b) {
    sb.append((char) b);
  }

  @Override
  public String toString() {
    return sb.toString();
  }

}
