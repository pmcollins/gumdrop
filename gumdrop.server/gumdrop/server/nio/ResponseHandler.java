package gumdrop.server.nio;

import gumdrop.server.HttpHeader;
import gumdrop.server.HttpRequest;
import gumdrop.server.RequestParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;

class ResponseHandler implements Runnable {

  private final Selector selector;
  private final SelectionKey selectionKey;
  private final RawExchange exchange;

  ResponseHandler(Selector selector, SelectionKey selectionKey, RawExchange exchange) {
    this.selector = selector;
    this.selectionKey = selectionKey;
    this.exchange = exchange;
  }

  @Override
  public void run() {
    String requestStr = exchange.getRequest();
    RequestParser requestParser = new RequestParser(requestStr);
    HttpRequest httpRequest = requestParser.parse();

    String body = "path: [" + httpRequest.getPath() + "]\n";
    HttpHeader httpHeader = new HttpHeader();
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
    exchange.setResponse(bb);
    selectionKey.interestOps(SelectionKey.OP_WRITE);
    selector.wakeup();
  }

}
