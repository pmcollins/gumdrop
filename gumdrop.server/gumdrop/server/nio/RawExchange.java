package gumdrop.server.nio;

import gumdrop.common.http.HttpRequest;

import java.nio.ByteBuffer;

class RawExchange {

  private final InterruptibleRequestParser parser = new InterruptibleRequestParser();
  private ByteBuffer response;

  void addRequestChunk(ByteBuffer bb) {
    parser.parse(bb);
  }

  HttpRequest getRequest() {
    return parser.getRequest();
  }

  boolean done() {
    return parser.done();
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
