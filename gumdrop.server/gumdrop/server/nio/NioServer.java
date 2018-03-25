package gumdrop.server.nio;

import gumdrop.common.http.HttpRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static java.net.StandardSocketOptions.SO_RCVBUF;

public class NioServer {

  private static final int N_THREADS = 4;

  private final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
  private final ByteBuffer bb;
  private final Selector selector;
  private final ServerSocketChannel serverSocketChannel;
  private final Function<HttpRequest, byte[]> mainFcn;

  public NioServer(Function<HttpRequest, byte[]> mainFcn, int port) throws IOException {
    this.mainFcn = mainFcn;
    serverSocketChannel = ServerSocketChannel.open();
    int receiveBufferSize = serverSocketChannel.getOption(SO_RCVBUF);
    bb = ByteBuffer.allocate(receiveBufferSize);
    serverSocketChannel.configureBlocking(false);
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress(port));
    selector = SelectorProvider.provider().openSelector();
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  }

  public void run() throws IOException {
    while (true) {
      selector.select();
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> it = selectionKeys.iterator();
      while (it.hasNext()) {
        SelectionKey selectionKey = it.next();
        it.remove();
        if (selectionKey.isValid()) {
          handleSelectionKey(selectionKey);
        }
      }
    }
  }

  private void handleSelectionKey(SelectionKey selectionKey) throws IOException {
    if (selectionKey.isAcceptable()) {
      handleAccept();
    } else if (selectionKey.isReadable()) {
      handleRead(selectionKey);
    } else if (selectionKey.isWritable()) {
      handleWrite(selectionKey);
    }
  }

  private void handleAccept() throws IOException {
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ);
  }

  private void handleRead(SelectionKey selectionKey) throws IOException {
    RawExchange exchange = (RawExchange) selectionKey.attachment();
    if (exchange == null) {
      exchange = new RawExchange();
      selectionKey.attach(exchange);
    }
    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
    int bytesRead = socketChannel.read(bb);
    if (bytesRead == -1) {
      socketChannel.close();
      System.err.println("premature end of stream reached on socket read");
    } else {
      bb.flip();
      exchange.addRequestChunk(bb);
      bb.clear();
      if (exchange.done()) {
        executorService.submit(
          new ResponseHandler(mainFcn, selector, selectionKey, exchange)
        );
      }
    }
  }

  private void handleWrite(SelectionKey selectionKey) throws IOException {
    RawExchange exchange = (RawExchange) selectionKey.attachment();
    if (exchange == null) {
      throw new IllegalStateException("exchange attachment null for key [" + selectionKey + "]");
    }
    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
    socketChannel.write(exchange.getResponse());
    if (!exchange.hasResponseRemaining()) {
      socketChannel.close();
    }
  }

}
