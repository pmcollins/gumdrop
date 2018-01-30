package gumdrop.server.nio;

import gumdrop.common.CharIterator;

import java.nio.ByteBuffer;

public class IncrementalRequestParser {

  public static String bbToString(ByteBuffer bb) {
    byte[] bytes = new byte[bb.limit()];
    bb.get(bytes);
    return new String(bytes);
  }

  private final LineReaderDelegate delegate;
  private CharIterator it;

  public IncrementalRequestParser(LineReaderDelegate delegate) {
    this.delegate = delegate;
  }

  void append(ByteBuffer bb) {
    append(bbToString(bb));
  }

  public void append(String str) {
    System.out.println(str);
    if (it == null) {
      it = new CharIterator(str);
    } else {
      it.append(str);
    }
  }

  public void readLines() {
    while (!it.done()) {
      readLine();
    }
    delegate.endOfChunk(it.substring());
  }

  public void readLine() {
    while (it.current() != '\n') {
      it.increment();
      if (it.done()) return;
    }
    String substring = it.substring(-1);
    delegate.line(substring);
    it.increment(); // step over the \n
    it.mark();
  }

}
