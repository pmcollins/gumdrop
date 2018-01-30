package gumdrop.server.nio;

import gumdrop.common.CharIterator;

public class HttpReader {

  private final CharIterator it;
  private final HttpReaderDelegate delegate;

  public HttpReader(String http, HttpReaderDelegate delegate) {
    it = new CharIterator(http);
    this.delegate = delegate;
  }

  private void readWord() {
    it.mark();
    char c = it.current();
    while (c != ' ' && c != '\r') {
      c = it.next();
    }
    delegate.word(it.substring());
  }

  public void readKVPair() {
    readKey();
    nextLine();
    readValue();
  }

  private void readKey() {
    readUntil(ch -> ch != ':');
    delegate.key(it.substring());
  }

  private void readValue() {
    readUntil(ch -> ch != '\r');
    delegate.value(it.substring());
  }

  private void readUntil(CharPredicate charPredicate) {
    it.mark();
    char c = it.current();
    while (charPredicate.test(c)) {
      c = it.next();
    }
  }

  public void readAll() {
    readFirstLine();
    while (it.current() != '\r') {
      readKVPair();
      nextLine();
    }
    char current = it.current();
    if (current == '\r') {
      // POST
      nextLine();
      it.mark();
      delegate.post(it.remaining());
    }
  }

  public void readFirstLine() {
    readWord();
    it.increment();
    readWord();
    it.increment();
    readWord();
    nextLine();
  }

  public void nextLine() {
    it.increment();
    it.increment();
  }

  private interface CharPredicate {
    boolean test(char c);
  }

}
