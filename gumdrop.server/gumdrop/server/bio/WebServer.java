package gumdrop.server.bio;

import gumdrop.server.HttpHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class WebServer {

  private final int port;
  private final RequestHandler<String> stringRequestHandler;

  WebServer(int port, RequestHandler<String> stringRequestHandler) {
    this.port = port;
    this.stringRequestHandler = stringRequestHandler;
  }

  void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(port);
    while (true) {
      handleRequest(serverSocket);
    }
  }

  private void handleRequest(ServerSocket serverSocket) throws IOException {
    Socket socket = serverSocket.accept();
    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();
    stringRequestHandler.handle(getRequestString(in), out);
    in.close();
    out.close();
    socket.close();
  }

  private static String getRequestString(InputStream in) throws IOException {
    StringBuilder sb = new StringBuilder();
    while (true) {
      int numBytesAvailable = in.available();
      byte[] chunk = new byte[numBytesAvailable];
      in.read(chunk);
      String chunkString = new String(chunk);
      sb.append(chunkString);
      if (chunkString.endsWith(HttpHeader.EOL + HttpHeader.EOL)) break;
    }
    return sb.toString();
  }

}
