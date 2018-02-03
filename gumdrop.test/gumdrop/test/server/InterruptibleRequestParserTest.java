package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.server.nio.*;
import gumdrop.test.util.Test;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

import static gumdrop.test.util.Asserts.*;

public class InterruptibleRequestParserTest extends Test {

  public static void main(String[] args) {
    new InterruptibleRequestParserTest().run();
  }

  @Override
  public void run() {
    fwdDelimiterTest();
    fwdDelimiterTest2();
    fwdDelimiterTest3();
    wordAccumulator();
    wordAccumulator2();
    wordAccumulator3();
    two();
    ten();
    twenty();
    fifty();
    fullSingleAttribute();
    singleAttributeInterrupted();
    twoAttributes();
    twoAttributesScan();
    emptyAttribute();
  }

  private static final String GET_STR =
    "GET / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "User-Agent: Mozilla\r\n\r\n";

  private static final String POST_STR =
    "POST /users/create HTTP/1.1\r\nHost: localhost:8080\r\nContent-Type: application/x-www-form-urlencoded\r\n" +
    "Origin: http://localhost:8080\r\nAccept-Encoding: gzip, deflate\r\nConnection: keep-alive\r\n" +
    "Upgrade-Insecure-Requests: 1\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) " +
    "Version/11.0.2 Safari/604.4.7\r\nReferer: http://localhost:8080/users/form\r\nContent-Length: 28\r\n" +
    "Accept-Language: en-us\r\n\r\nfirst=qqq&last=www&email=eee";

  private static void testHead(int splitPt, Consumer<InterruptibleRequestParser> test) {
    String head = GET_STR.substring(0, splitPt);
    InterruptibleRequestParser parser = new InterruptibleRequestParser();
    parser.parse(wrap(head));

    test.accept(parser);

    String tail = GET_STR.substring(splitPt, GET_STR.length());
    parser.parse(wrap(tail));
    assertHead(parser);
  }

  private static void assertHead(InterruptibleRequestParser parser) {
    assertEquals("GET", parser.getMethod());
    assertEquals("/", parser.getPath());
    assertEquals("HTTP/1.1", parser.getProtocol());
  }

  private static ByteBuffer wrap(String string) {
    return ByteBuffer.wrap(string.getBytes());
  }

  // -------------------------------------------------------------------------------------------------------------------

  private void fwdDelimiterTest() {
    FwdDelimiter delim = new FwdDelimiter("cde");
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(delim.match(it));
    it.position(2);
    assertEquals('c', it.current());
    assertTrue(delim.match(it));
    assertEquals('c', it.current());
  }

  private void fwdDelimiterTest2() {
    FwdDelimiter d = new FwdDelimiter("cdefghijk");
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(d.match(it));
    it.position(2);
    assertEquals('c', it.current());
    assertFalse(d.match(it));
    assertEquals('c', it.current());
  }

  private void fwdDelimiterTest3() {
    FwdDelimiter d = new FwdDelimiter("ccc");
    CharIterator it = new CharIterator("abcdefg");
    assertFalse(d.match(it));
  }

  private void wordAccumulator() {
    WordAccumulator accumulator = new WordAccumulator("\r\n");
    assertTrue(accumulator.match(new CharIterator("XXX\r\nZZZ")));
    String val = accumulator.getVal();
    assertEquals("XXX", val);
    CharIterator it = new CharIterator("XXX\rYYY\r\nZZZ");
    assertTrue(accumulator.match(it));
    assertEquals("XXX\rYYY", accumulator.getVal());
    assertEquals('\r', it.current());
  }

  private void wordAccumulator2() {
    WordAccumulator accumulator = new WordAccumulator("\r\n");
    CharIterator it = new CharIterator("XXX\r");
    assertFalse(accumulator.match(it));
    it.append("\n");
    assertTrue(accumulator.match(it));
    assertEquals("XXX", accumulator.getVal());
  }

  private void wordAccumulator3() {
    WordAccumulator accumulator = new WordAccumulator("Hello");
    CharIterator it = new CharIterator("XXXHell");
    assertFalse(accumulator.match(it));
    it.append("xYYYHello");
    assertTrue(accumulator.match(it));
    assertEquals("XXXHellxYYY", accumulator.getVal());
  }

  private void two() {
    testHead(2, parser -> assertNull(parser.getMethod()));
  }

  private void ten() {
    testHead(10, parser -> {
      assertEquals("GET", parser.getMethod());
      assertEquals("/", parser.getPath());
      assertNull(parser.getProtocol());
    });
  }

  private void twenty() {
    int splitPt = 20;
    String head = GET_STR.substring(0, splitPt);
    InterruptibleRequestParser parser = new InterruptibleRequestParser();
    parser.parse(wrap(head));
    assertHead(parser);
  }

  private void fifty() {
    int splitPt = 50;
    String head = GET_STR.substring(0, splitPt);
    InterruptibleRequestParser parser = new InterruptibleRequestParser();
    parser.parse(wrap(head));
  }

  private void fullSingleAttribute() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    boolean completed = accumulator.match(new CharIterator("Host: localhost:8080\r\n"));
    assertTrue(completed);
  }

  private void emptyAttribute() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    boolean complete = accumulator.match(new CharIterator("\r\n"));
    assertFalse(complete);
  }

  private void singleAttributeInterrupted() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    String line = "Host: localhost:8080\r\n";
    int splitPt = 2;
    String head = line.substring(0, splitPt);
    String tail = line.substring(splitPt, line.length());
    CharIterator it = new CharIterator(head);
    assertFalse(accumulator.match(it));
    it.append(tail);
    assertTrue(accumulator.match(it));
    assertEquals("Host", accumulator.getKey());
    assertEquals("localhost:8080", accumulator.getValue());
  }

  private void twoAttributes() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    boolean completed = accumulator.match(new CharIterator(lines));
    assertTrue(completed);
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

  private void twoAttributesScan() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    for (int i = 0; i < lines.length(); i++) {
      testSplit(lines, i);
    }
  }

  private void testSplit(String lines, int splitPt) {
    String head = lines.substring(0, splitPt);
    String tail = lines.substring(splitPt, lines.length());
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    CharIterator it = new CharIterator(head);
    assertFalse(accumulator.match(it));
    it.append(tail);
    assertTrue(accumulator.match(it));
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

}
