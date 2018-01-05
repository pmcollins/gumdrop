package gumdrop.server.bio;

import java.io.IOException;

public class WebServerMain {

  public static void main(String[] args) throws IOException {
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    StringRequestHandler stringRequestHandler = new StringRequestHandler(httpRequestHandler);
    WebServer server = new WebServer(8080, stringRequestHandler);
    server.run();
  }

}
