package gumdrop.test.server;

import gumdrop.common.CharIterator;
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
//    emptyAttribute();
//    backwardDelimiterTest();
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
    System.out.println("splitPt = [" + splitPt + "]");
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

  private void backwardDelimiterTest() {
    BackwardDelimiter d = new BackwardDelimiter("cde");
    CharIterator it = new CharIterator("abcdefg");
    for (int i = 0; i < 4; i++) {
      assertFalse(d.match(it));
      it.increment();
    }
    assertEquals('e', it.current());
    assertTrue(d.match(it));
    it.increment();
    assertFalse(d.match(it));
  }

}

class FwdDelimiter implements Accumulator {

  private final CharIterator delim;

  FwdDelimiter(String s) {
    delim = new CharIterator(s);
  }

  @Override
  public boolean match(CharIterator it) {
    int itPos = it.position();
    boolean out = false;
    while (it.current() == delim.current()) {
      it.increment();
      delim.increment();
      if (delim.done()) {
        out = true;
        break;
      }
    }
    reset();
    it.position(itPos);
    return out;
  }

  public boolean matching() {
    return delim.position() > 0;
  }

  public int length() {
    return delim.length();
  }

  private void reset() {
    delim.position(0);
  }

}

class BackwardDelimiter implements Accumulator {

  private final CharIterator delim;
  private final int len;

  BackwardDelimiter(String s) {
    delim = new CharIterator(s);
    len = delim.length();
    reset();
  }

  private void reset() {
    delim.position(len - 1);
  }

  @Override
  public boolean match(CharIterator it) {
    int itPosition = it.position();
    boolean out = false;
    while (delim.current() == it.current()) {
      if (delim.position() == 0) {
        out = true;
        // if we matched, step over the last matching character
        ++itPosition;
        break;
      } else if (it.position() == 0) {
        break;
      } else {
        delim.decrement();
        it.decrement();
      }
    }
    reset();
    it.position(itPosition);
    return out;
  }

  public int length() {
    return delim.length();
  }

}

class AttributeCollectionAccumulator implements Accumulator {

  private final List<AttributeAccumulator> accumulators = new ArrayList<>();
  private AttributeAccumulator curr;
  private final FwdDelimiter delimiter = new FwdDelimiter("\r\n");

  @Override
  public boolean match(CharIterator it) {
    if (curr == null) {
      if (delimiter.match(it)) return true;
      curr = new AttributeAccumulator();
    }
    while (true) {
      if (curr.match(it)) {
        it.increment(); // \r
        it.increment(); // \n
        it.mark();
        accumulators.add(curr);
        if (delimiter.match(it)) return true;
        if (it.remaining() < delimiter.length()) {
          // We just matched. At this point, we're pointing to the beginning of a new line, yet that line is mostly empty.
          // Don't leave an AttributeAccumulator which will preempt a possible delimiter match on the next round.
          curr = null;
          return false;
        } else {
          curr = new AttributeAccumulator();
        }
      } else {
        return false;
      }
    }
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
  public boolean match(CharIterator it) {
    if (key.done()) {
      return value.match(it);
    } else {
      boolean keyMatch = key.match(it);
      //noinspection SimplifiableIfStatement
      if (keyMatch) {
        it.increment(); // :
        it.increment(); // ' '
        it.mark();
        return value.match(it);
      }
      return false;
    }
  }

  public String getKey() {
    return key.getVal();
  }

  public String getValue() {
    return value.getVal();
  }

}

interface Accumulator {

  boolean match(CharIterator it);

}

class WordAccumulator implements Accumulator {

  private String val;
  private final FwdDelimiter delim;

  WordAccumulator(char stopChar) {
    this(String.valueOf(stopChar));
  }

  WordAccumulator(String s) {
    delim = new FwdDelimiter(s);
  }

  public boolean match(CharIterator it) {
    while (!it.done()) {
      if (delim.match(it)) {
        val = it.substring();
        return true;
      }
      if (it.remaining() < delim.length()) {
        return false;
      }
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
    while (curr.match(it) && ptr.hasNext()) {
      curr = ptr.next();
      it.increment(); // step over the end of the delimiter
      it.mark();
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
