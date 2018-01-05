package gumdrop.server.bio;

import java.io.IOException;
import java.io.OutputStream;

class StringRequestHandler implements RequestHandler<String> {

  private final RequestHandler<HttpRequest> requestHandler;

  StringRequestHandler(RequestHandler<HttpRequest> requestHandler) {
    this.requestHandler = requestHandler;
  }

  @Override
  public void handle(String request, OutputStream out) throws IOException {
    RequestParser requestParser = new RequestParser(request);
    HttpRequest httpRequest = requestParser.parse();
    requestHandler.handle(httpRequest, out);
  }

}
