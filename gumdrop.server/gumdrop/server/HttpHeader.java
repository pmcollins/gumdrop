package gumdrop.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import static gumdrop.server.WebServer.EOL;

class HttpHeader {

  private int length;

  void setLength(int length) {
    this.length = length;
  }

  byte[] bytes() throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    writeTo(os);
    return os.toByteArray();
  }

  void writeTo(OutputStream os) throws IOException {
    StringOutputStream sos = new StringOutputStream(os);
    HttpHeaderData h = createHeaderData();
    sos.write(h.getProtocol()).write(" ").write(h.getStatus()).write(EOL);
    for (Map.Entry<String, String> e : h.getAttrs().entrySet()) {
      sos.write(e.getKey()).write(": ").write(e.getValue()).write(EOL);
    }
    sos.write(EOL);
  }

  private HttpHeaderData createHeaderData() {
    HttpHeaderData h = new HttpHeaderData();
    h.setProtocol("HTTP/1.1");
    h.setStatus("200 OK");
    h.putAttr("Content-Type", "text/plain; charset=UTF-8");
    h.putAttr("Content-Length", String.valueOf(length));
    return h;
  }

}
