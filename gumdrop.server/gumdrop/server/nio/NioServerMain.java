package gumdrop.server.nio;

import java.io.IOException;

public class NioServerMain {

  public static void main(String[] args) throws IOException {
    NioServer server = new NioServer(8080);
    server.run();
  }

}
