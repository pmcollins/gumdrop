package gumdrop.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelector;

class ResponseHandler implements Runnable {

  private final AbstractSelector selector;
  private final SelectionKey selectionKey;
  private final RawExchange exchange;

  ResponseHandler(AbstractSelector selector, SelectionKey selectionKey, RawExchange exchange) {
    this.selector = selector;
    this.selectionKey = selectionKey;
    this.exchange = exchange;
  }

  @Override
  public void run() {
    String body = Thread.currentThread().toString() + '\n';
    HttpHeader httpHeader = new HttpHeader();
    int bodyLength = body.length();
    httpHeader.setLength(bodyLength);
    byte[] headerBytes = new byte[0];
    try {
      headerBytes = httpHeader.bytes();
    } catch (IOException e) {
      e.printStackTrace();
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
