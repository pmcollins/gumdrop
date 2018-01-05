package gumdrop.server.nio;

import java.nio.ByteBuffer;

class RawExchange {

  private final StringBuilder request = new StringBuilder(1024);
  private ByteBuffer response;

  void addRequestChunk(ByteBuffer bb) {
    while (bb.hasRemaining()) {
      request.append((char) bb.get());
    }
  }

  private String getRequest() {
    return request.toString();
  }

  boolean isDoneReading() {
    return request.substring(request.length() - 4).equals("\r\n\r\n");
  }

  ByteBuffer getResponse() {
    return response;
  }

  void setResponse(ByteBuffer response) {
    this.response = response;
  }

  boolean hasResponseRemaining() {
    return response.hasRemaining();
  }

}
