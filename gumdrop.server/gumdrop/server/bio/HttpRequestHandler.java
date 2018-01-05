package gumdrop.server.bio;

import gumdrop.server.HttpHeader;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;

class HttpRequestHandler implements RequestHandler<HttpRequest> {

  @Override
  public void handle(HttpRequest httpRequest, OutputStream out) throws IOException {
    if ("/favicon/ico".equals(httpRequest.getPath())) {
      out.write("HTTP/1.1 404".getBytes());
    } else {
      String msg = Instant.now().toString() + "\n";
      byte[] bytes = msg.getBytes();
      writeBytes(out, bytes);
    }
  }

  private void writeBytes(OutputStream out, byte[] bytes) throws IOException {
    int length = bytes.length;
    writeHeader(out, length);
    out.write(bytes);
  }

  private void writeHeader(OutputStream out, int length) throws IOException {
    HttpHeader httpHeader = new HttpHeader();
    httpHeader.setLength(length);
    httpHeader.writeTo(out);
  }

}
