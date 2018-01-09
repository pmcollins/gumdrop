package gumdrop.server.nio;

import gumdrop.common.HttpRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.function.Function;

class ResponseHandler implements Runnable {

  private final Function<HttpRequest, byte[]> mainFcn;
  private final Selector selector;
  private final SelectionKey selectionKey;
  private final RawExchange exchange;

  ResponseHandler(Function<HttpRequest, byte[]> mainFcn, Selector selector, SelectionKey selectionKey, RawExchange exchange) {
    this.mainFcn = mainFcn;
    this.selector = selector;
    this.selectionKey = selectionKey;
    this.exchange = exchange;
  }

  @Override
  public void run() {
    try {
      doRun();
    } catch (Exception x) {
      x.printStackTrace();
      try {
        selectionKey.channel().close();
      } catch (IOException closeX) {
        closeX.printStackTrace();
      }
    }
  }

  private void doRun() {
    HttpRequest request = exchange.getRequest();
    byte[] response = mainFcn.apply(request);
    exchange.setResponse(ByteBuffer.wrap(response));
    selectionKey.interestOps(SelectionKey.OP_WRITE);
    selector.wakeup();
  }

}
