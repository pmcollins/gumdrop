package gumdrop.server;

import java.io.IOException;
import java.io.OutputStream;

interface RequestHandler<T> {

  void handle(T request, OutputStream outputStream) throws IOException;

}
