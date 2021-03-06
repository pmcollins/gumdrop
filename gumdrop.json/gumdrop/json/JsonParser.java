package gumdrop.json;

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
        String quotedString = readQuotedString();
        delegate.acceptString(quotedString);
        break;
      case '{':
        readObject();
        break;
      case '[':
        readArray();
        break;
      default:
        String bareword = readBareword();
        delegate.acceptBareword(bareword);
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
    delegate.pop();
  }

  private void readArray() {
    delegate.push();
    it.increment();
    readCommaList();
    it.increment();
    delegate.pop();
  }

  private void readCommaList() {
    if (it.currentChar() == ']') return;
    delegate.push();
    readValue();
    delegate.pop();
    while (it.currentChar() == ',') {
      it.increment();
      delegate.push();
      readValue();
      delegate.pop();
    }

  }

  private void skipWhiteSpace() {
    char c;
    while ((c = it.currentChar()) == ' ' || c == '\n' || c == '\t') {
      it.increment();
    }
  }

}
