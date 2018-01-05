package gumdrop.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponseHeader {

  public static final String EOL = "\r\n";
  private int length;

  public void setLength(int length) {
    this.length = length;
  }

  public byte[] bytes() throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    writeTo(os);
    return os.toByteArray();
  }

  public void writeTo(OutputStream os) throws IOException {
    StringOutputStream sos = new StringOutputStream(os);
    HttpResponseHeaderData h = createHeaderData();
    sos.write(h.getProtocol()).write(" ").write(h.getStatus()).write(EOL);
    for (Map.Entry<String, String> e : h.getAttrs().entrySet()) {
      sos.write(e.getKey()).write(": ").write(e.getValue()).write(EOL);
    }
    sos.write(EOL);
  }

  private HttpResponseHeaderData createHeaderData() {
    HttpResponseHeaderData h = new HttpResponseHeaderData();
    h.setProtocol("HTTP/1.1");
    h.setStatus("200 OK");
    h.putAttr("Content-Type", "text/plain; charset=UTF-8");
    h.putAttr("Content-Length", String.valueOf(length));
    return h;
  }

}
