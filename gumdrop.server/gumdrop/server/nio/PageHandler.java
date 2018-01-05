package gumdrop.server.nio;

import gumdrop.server.HttpResponseHeader;

import java.io.IOException;
import java.nio.ByteBuffer;

public class PageHandler extends RestHandler {

  @Override
  protected ByteBuffer render() {
    String body = Thread.currentThread().toString() + ": " + getClass().toString() + "\n";
    HttpResponseHeader httpHeader = new HttpResponseHeader();
    int bodyLength = body.length();
    httpHeader.setLength(bodyLength);
    byte[] headerBytes;
    try {
      headerBytes = httpHeader.bytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ByteBuffer bb = ByteBuffer.allocate(headerBytes.length + bodyLength);
    bb.put(headerBytes);
    bb.put(body.getBytes());
    bb.flip();
    return bb;
  }

}
