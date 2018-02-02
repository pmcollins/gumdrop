package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.test.util.Test;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

import static gumdrop.test.util.Asserts.*;

public class InterruptibleRequestParserTest extends Test {

  private static void split(int splitPt, Consumer<InterruptibleRequestParser> test) {
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
    new InterruptibleRequestParserTest().run();
  }

  @Override
  public void run() {
    wordAccumulator();
    two();
    ten();
    twenty();
    fifty();
    fullSingleAttribute();
    singleAttributeInterrupted();
    twoAttributes();
    twoAttributesInterrupted();
  }

  private void wordAccumulator() {
    WordAccumulator accumulator = new WordAccumulator("Hello");
    assertTrue(accumulator.complete(new CharIterator("XXXHelloZZZ")));
    String val = accumulator.getVal();
    assertEquals("XXX", val);
    assertTrue(accumulator.complete(new CharIterator("XXXHellYYYHelloZZZ")));
    assertEquals("XXXHellYYY", accumulator.getVal());
  }

  private void two() {
    split(2, parser -> assertNull(parser.getMethod()));
  }

  private void ten() {
    split(10, parser -> {
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
    boolean completed = accumulator.complete(new CharIterator("Host: localhost:8080\r\n"));
    assertTrue(completed);
  }

  private void singleAttributeInterrupted() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    String line = "Host: localhost:8080\r\n";
    int splitPt = 2;
    String head = line.substring(0, splitPt);
    String tail = line.substring(splitPt, line.length());
    CharIterator it = new CharIterator(head);
    assertFalse(accumulator.complete(it));
    it.append(tail);
    assertTrue(accumulator.complete(it));
    assertEquals("Host", accumulator.getKey());
    assertEquals("localhost:8080", accumulator.getValue());
  }

  private void twoAttributes() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    boolean completed = accumulator.complete(new CharIterator(lines));
    assertTrue(completed);
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

  private void twoAttributesInterrupted() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    for (int i = 0; i < lines.length() - 2; i++) {
      testSplit(lines, i);
    }
  }

  private void testSplit(String lines, int splitPt) {
    System.out.println("splitPt = [" + splitPt + "]");
    String head = lines.substring(0, splitPt);
    String tail = lines.substring(splitPt, lines.length());
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    CharIterator it = new CharIterator(head);
    assertFalse(accumulator.complete(it));
    it.append(tail);
    assertTrue(accumulator.complete(it));
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

}

class AttributeCollectionAccumulator implements Accumulator {

  private final List<AttributeAccumulator> accumulators = new ArrayList<>();
  private AttributeAccumulator curr;

  @Override
  public boolean complete(CharIterator it) {
    if (curr == null) {
      curr = new AttributeAccumulator();
    }
    while (curr.complete(it)) {
      accumulators.add(curr);
      curr = new AttributeAccumulator();
      if (it.current() == '\r') {
        return true;
      } else if (it.done()) {
        return false;
      }
    }
    return false;
  }

  Map<String, String> getMap() {
    Map<String, String> out = new HashMap<>();
    for (AttributeAccumulator accumulator : accumulators) {
      String key = accumulator.getKey();
      String value = accumulator.getValue();
      out.put(key, value);
    }
    return out;
  }

}

class AttributeAccumulator implements Accumulator {

  private final WordAccumulator key = new WordAccumulator(": ");
  private final WordAccumulator value = new WordAccumulator("\r\n");

  @Override
  public boolean complete(CharIterator it) {
    if (key.done()) {
      return value.complete(it);
    } else {
      return key.complete(it) && value.complete(it);
    }
  }

  public String getKey() {
    return key.getVal();
  }

  public String getValue() {
    return value.getVal();
  }

}

class InterruptibleRequestParser {

  private final WordAccumulator method = new WordAccumulator(' ');
  private final WordAccumulator path = new WordAccumulator(' ');
  private final WordAccumulator protocol = new WordAccumulator('\r');
  private final Iterator<Accumulator> ptr;

  private Accumulator curr;
  private CharIterator it;

  InterruptibleRequestParser() {
    List<Accumulator> accumulators = new ArrayList<>();
    accumulators.add(method);
    accumulators.add(path);
    accumulators.add(protocol);
    ptr = accumulators.iterator();
    curr = ptr.next();
  }

  void parse(ByteBuffer bb) {
    String str = new String(bb.array());
    if (it == null) {
      it = new CharIterator(str);
    } else {
      it.append(str);
    }
    while (curr.complete(it) && ptr.hasNext()) {
      curr = ptr.next();
    }
  }

  String getMethod() {
    return method.getVal();
  }

  String getPath() {
    return path.getVal();
  }

  String getProtocol() {
    return protocol.getVal();
  }

}

interface Accumulator {

  boolean complete(CharIterator it);

}

class WordAccumulator implements Accumulator {

  private String val;
  private final CharIterator delim;

  WordAccumulator(char stopChar) {
    this(String.valueOf(stopChar));
  }

  WordAccumulator(String s) {
    delim = new CharIterator(s);
  }

  public boolean complete(CharIterator it) {
    while (!it.done()) {
      char c = it.current();
      if (delim.current() == c) {
        delim.increment();
        it.increment();
        if (delim.done()) {
          // strip off the delimiter
          val = it.substring(-delim.length());
          it.mark();
          return true;
        } else {
          continue;
        }
      }
      delim.reset();
      it.increment();
    }
    return false;
  }

  public String getVal() {
    return val;
  }

  boolean done() {
    return val != null;
  }

}
