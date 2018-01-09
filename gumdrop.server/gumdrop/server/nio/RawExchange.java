package gumdrop.server.nio;

import gumdrop.common.HttpRequest;

import java.nio.ByteBuffer;

class RawExchange {

  private final RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
  private final IncrementalRequestParser parser = new IncrementalRequestParser(delegate);
  private ByteBuffer response;

  void addRequestChunk(ByteBuffer bb) {
    parser.append(bb);
    parser.readLines();
  }

  HttpRequest getRequest() {
    return delegate.getRequest();
  }

  boolean isDoneReading() {
    return delegate.getRequest().isCompleted();
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
