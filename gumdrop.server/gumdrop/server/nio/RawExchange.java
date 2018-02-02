package gumdrop.server.nio;

import gumdrop.common.HttpRequest;

import java.nio.ByteBuffer;

class RawExchange {

  private final RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
  private final RequestParser parser = new LineOrientedRequestParser(delegate);
  private ByteBuffer response;

  void addRequestChunk(ByteBuffer bb) {
    parser.append(bb);
    parser.parse();
  }

  HttpRequest getRequest() {
    return delegate.getRequest();
  }

  boolean isDoneReading() {
    return delegate.isCompleted();
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
