package gumdrop.server.nio;

public interface LineReaderDelegate {

  void line(String line);

  void endOfChunk(String remainder);

}
