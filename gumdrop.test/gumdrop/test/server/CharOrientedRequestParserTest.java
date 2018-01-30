package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.test.util.Test;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertTrue;

public class CharOrientedRequestParserTest extends Test {

  private static final String GET_STR =
    "GET / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "User-Agent: Mozilla\r\n\r\n";

  private static final String POST_STR =
    "POST /users/create HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "Content-Type: application/x-www-form-urlencoded\r\n" +
    "Origin: http://localhost:8080\r\n" +
    "Accept-Encoding: gzip, deflate\r\n" +
    "Connection: keep-alive\r\n" +
    "Upgrade-Insecure-Requests: 1\r\n" +
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) " +
      "Version/11.0.2 Safari/604.4.7\r\n" +
    "Referer: http://localhost:8080/users/form\r\n" +
    "Content-Length: 28\r\n" +
    "Accept-Language: en-us\r\n" +
    "\r\n" +
    "first=qqq&last=www&email=eee";

  public static void main(String[] args) {
    new CharOrientedRequestParserTest().run();
  }

  @Override
  public void run() {
    firstLine();
    hostLine();
    get();
    autoGet();
    post();
  }

  private void firstLine() {
    FakeHttpReaderDelegate delegate = new FakeHttpReaderDelegate();
    HttpReader httpReader = new HttpReader("GET / HTTP/1.1\r\n", delegate);
    httpReader.readFirstLine();
    assertEquals(List.of("GET", "/", "HTTP/1.1"), delegate.getWords());
  }

  private void hostLine() {
    FakeHttpReaderDelegate delegate = new FakeHttpReaderDelegate();
    HttpReader httpReader = new HttpReader("Host: localhost:8080\r\n", delegate);
    httpReader.readKVPair();
    assertEquals(List.of("Host"), delegate.getKeys());
    assertEquals(List.of("localhost:8080"), delegate.getValues());
  }

  private void get() {
    FakeHttpReaderDelegate delegate = new FakeHttpReaderDelegate();
    HttpReader r = new HttpReader(GET_STR, delegate);
    r.readFirstLine();
    r.readKVPair();
    r.nextLine();
    r.readKVPair();
    assertEquals(List.of("GET", "/", "HTTP/1.1"), delegate.getWords());
    assertEquals(List.of("Host", "User-Agent"), delegate.getKeys());
    assertEquals(List.of("localhost:8080", "Mozilla"), delegate.getValues());
  }

  private void autoGet() {
    FakeHttpReaderDelegate delegate = new FakeHttpReaderDelegate();
    HttpReader r = new HttpReader(GET_STR, delegate);
    r.readAll();
    assertEquals(List.of("GET", "/", "HTTP/1.1"), delegate.getWords());
    assertEquals(List.of("Host", "User-Agent"), delegate.getKeys());
    assertEquals(List.of("localhost:8080", "Mozilla"), delegate.getValues());
  }

  private void post() {
    FakeHttpReaderDelegate delegate = new FakeHttpReaderDelegate();
    HttpReader r = new HttpReader(POST_STR, delegate);
    r.readAll();
    assertEquals(List.of("POST", "/users/create", "HTTP/1.1"), delegate.getWords());
    assertTrue(delegate.getKeys().contains("Accept-Language"));
    assertTrue(delegate.getValues().contains("en-us"));
    assertEquals("first=qqq&last=www&email=eee", delegate.getPost());
  }

}

class FakeHttpReaderDelegate implements HttpReaderDelegate {

  private final List<String> words = new ArrayList<>();
  private final List<String> keys = new ArrayList<>();
  private final List<String> values = new ArrayList<>();
  private String post;

  @Override
  public void word(String word) {
    words.add(word);
  }

  @Override
  public void key(String key) {
    keys.add(key);
  }

  @Override
  public void value(String value) {
    values.add(value);
  }

  @Override
  public void post(String post) {
    this.post = post;
  }

  public String getPost() {
    return post;
  }

  public List<String> getWords() {
    return words;
  }

  public List<String> getKeys() {
    return keys;
  }

  public List<String> getValues() {
    return values;
  }

}

interface HttpReaderDelegate {

  void word(String word);

  void key(String key);

  void value(String value);

  void post(String post);

}

class HttpReader {

  private final CharIterator it;
  private final HttpReaderDelegate delegate;

  HttpReader(String http, HttpReaderDelegate delegate) {
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
