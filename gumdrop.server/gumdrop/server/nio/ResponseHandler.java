package gumdrop.server.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.function.Function;

class ResponseHandler implements Runnable {

  private final Function<byte[], byte[]> mainFcn;
  private final Selector selector;
  private final SelectionKey selectionKey;
  private final RawExchange exchange;

  ResponseHandler(Function<byte[], byte[]> mainFcn, Selector selector, SelectionKey selectionKey, RawExchange exchange) {
    this.mainFcn = mainFcn;
    this.selector = selector;
    this.selectionKey = selectionKey;
    this.exchange = exchange;
  }

  @Override
  public void run() {
    exchange.setResponse(ByteBuffer.wrap(mainFcn.apply(exchange.getRequest().getBytes())));
    selectionKey.interestOps(SelectionKey.OP_WRITE);
    selector.wakeup();
  }

}
