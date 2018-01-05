package gumdrop.json;

public class JsonReader {

  private final CharIterator it;
  private final JsonDelegate delegate;

  public JsonReader(String json, JsonDelegate delegate) {
    it = new CharIterator(json);
    this.delegate = delegate;
  }

  public void readValue() {
    skipWhiteSpace();
    char curr = it.current();
    if (curr == '"') {
      readQuotedString();
    } else if (curr == '{') {
      readObject();
    } else if (curr == '[') {
      readArray();
    } else {
      readBareword();
    }
    skipWhiteSpace();
  }

  public void readObject() {
    delegate.objectStart();
    it.increment(); // '{'
    readKVPairs();
    it.increment(); // '}'
    delegate.objectEnd();
  }

  public void readQuotedString() {
    it.increment();
    it.mark();
    char c;
    do {
      c = it.next();
    } while (c != '"');
    delegate.quotedString(it.substring());
    it.increment();
  }

  public void readBareword() {
    it.mark();
    char c = it.current();
    while (Character.isAlphabetic(c) || Character.isDigit(c)) {
      c = it.next();
    }
    String bareword = it.substring();
    delegate.bareword(bareword);
  }

  public void readKVPair() {
    skipWhiteSpace();
    readQuotedString();
    skipWhiteSpace();
    it.increment(); // ':'
    readValue();
  }

  public void readKVPairs() {
    readKVPair();
    while (it.current() == ',') {
      it.increment();
      readKVPair();
    }
  }

  public void readArray() {
    delegate.arrayStart();
    it.increment();
    readCommaList();
    it.increment();
    delegate.arrayEnd();
  }

  public void readCommaList() {
    readValue();
    while (it.current() == ',') {
      it.increment();
      readValue();
    }
  }

  public void skipWhiteSpace() {
    char c;
    while ((c = it.current()) == ' ' || c == '\n' || c == '\t') {
      it.increment();
    }
  }

  public char getCurrentCharacter() {
    return it.current();
  }

}
