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
    two();
    ten();
    twenty();
    fifty();
//    post();
  }

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

  private void post() {
    for (int i = 0; i < POST_STR.length(); i++) {
      assertSplitPost(i);
    }
  }

  private void assertSplitPost(int splitPt) {
    String head = POST_STR.substring(0, splitPt);
    String tail = POST_STR.substring(splitPt, POST_STR.length());
    InterruptibleRequestParser parser = new InterruptibleRequestParser();
    parser.parse(wrap(head));
    assertFalse(parser.done());
    parser.parse(wrap(tail));
    assertTrue(parser.done());
    String method = parser.getMethod();
    assertEquals("POST", method);
    String path = parser.getPath();
    assertEquals("/users/create", path);
    String protocol = parser.getProtocol();
    assertEquals("HTTP/1.1", protocol);
    Map<String, String> attrs = parser.getAttrs();
    assertEquals("28", attrs.get("Content-Length"));
  }

}
