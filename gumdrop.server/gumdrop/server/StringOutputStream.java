package gumdrop.server;

import java.io.IOException;
import java.io.OutputStream;

class StringOutputStream {

  private final OutputStream out;

  StringOutputStream(OutputStream out) {
    this.out = out;
  }

  StringOutputStream write(String s) throws IOException {
    out.write(s.getBytes());
    return this;
  }

}
