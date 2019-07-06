package gumdrop.json.v2;

import gumdrop.common.ByteIterator;

public class JsonParser {

  private final ByteIterator it;
  private final JsonDelegate delegate;

  public JsonParser(JsonDelegate delegate, String json) {
    it = new ByteIterator(json);
    this.delegate = delegate;
  }

  public void readValue() {
    skipWhiteSpace();
    char curr = it.currentChar();
    switch (curr) {
      case '"':
        delegate.pop(readQuotedString());
        break;
      case '{':
        readObject();
        break;
      case '[':
        readArray();
        break;
      default:
        delegate.pop(readBareword());
        break;
    }
    skipWhiteSpace();
  }

  private void readObject() {
    delegate.push();
    it.increment(); // '{'
    skipWhiteSpace();
    if (it.currentChar() != '}') {
      readKVPairs();
    }
    it.increment(); // '}'
    delegate.pop();
  }

  private String readQuotedString() {
    it.increment();
    it.mark();
    char c;
    do {
      c = it.nextChar();
    } while (c != '"');
    String substring = it.substring();
    it.increment();
    return substring;
  }

  private String readBareword() {
    it.mark();
    char c = it.currentChar();
    while (Character.isAlphabetic(c) || Character.isDigit(c)) {
      c = it.nextChar();
    }
    return it.substring();
  }

  private void readKVPairs() {
    readKVPair();
    while (it.currentChar() == ',') {
      it.increment();
      readKVPair();
    }
  }

  private void readKVPair() {
    skipWhiteSpace();
    String s = readQuotedString();
    delegate.push(s);
    skipWhiteSpace();
    it.increment(); // ':'
    readValue();
  }

  private void readArray() {
    delegate.push();
    it.increment();
    readCommaList();
    it.increment();
    delegate.pop();
  }

  private void readCommaList() {
    delegate.push();
    readValue();
    while (it.currentChar() == ',') {
      it.increment();
      delegate.push();
      readValue();
    }
  }

  private void skipWhiteSpace() {
    char c;
    while ((c = it.currentChar()) == ' ' || c == '\n' || c == '\t') {
      it.increment();
    }
  }

}
