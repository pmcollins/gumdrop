package gumdrop.server.nio;

import gumdrop.common.CharIterator;

import java.nio.ByteBuffer;

public class IncrementalRequestParser {

  private final LineReaderDelegate delegate;
  private CharIterator it;

  public IncrementalRequestParser(LineReaderDelegate delegate) {
    this.delegate = delegate;
  }

  public static String bbToString(ByteBuffer bb) {
    byte[] bytes = new byte[bb.limit()];
    bb.get(bytes);
    return new String(bytes);
  }

  void append(ByteBuffer bb) {
    append(bbToString(bb));
  }

  public void append(String str) {
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
    delegate.endOfDoc(it.substring());
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
