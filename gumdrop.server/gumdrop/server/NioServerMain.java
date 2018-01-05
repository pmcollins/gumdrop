package gumdrop.server;

import java.io.IOException;

public class NioServerMain {

  public static void main(String[] args) throws IOException {
    NioServer server = new NioServer();
    server.run();
  }

}
