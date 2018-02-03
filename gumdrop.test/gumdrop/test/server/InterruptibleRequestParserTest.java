package gumdrop.test.server;

import gumdrop.server.nio.*;
import gumdrop.test.util.Test;

import java.nio.ByteBuffer;

import static gumdrop.test.util.Asserts.*;

public class InterruptibleRequestParserTest extends Test {

  public static void main(String[] args) {
    new InterruptibleRequestParserTest().run();
  }

  @Override
  public void run() {
    scanGet();
    scanPost();
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

  private static void assertHead(InterruptibleRequestParser parser) {
    assertEquals("GET", parser.getMethod());
    assertEquals("/", parser.getPath());
    assertEquals("HTTP/1.1", parser.getProtocol());
  }

  private static ByteBuffer wrap(String string) {
    return ByteBuffer.wrap(string.getBytes());
  }

  private void scanGet() {
    String req = GET_STR;
    for (int splitPt = 0; splitPt < req.length(); splitPt++) {
      String head = req.substring(0, splitPt);
      String tail = req.substring(splitPt, req.length());
      InterruptibleRequestParser parser = new InterruptibleRequestParser();
      parser.parse(wrap(head));
      assertFalse(parser.done());
      parser.parse(wrap(tail));
      assertTrue(parser.done());
      assertHead(parser);
    }
  }

  private void scanPost() {
    assertPost(510);
    for (int splitPt = 0; splitPt < POST_STR.length(); splitPt++) {
      assertPost(splitPt);
    }
  }

  private void assertPost(int splitPt) {
    String head = POST_STR.substring(0, splitPt);
    String tail = POST_STR.substring(splitPt, POST_STR.length());
    InterruptibleRequestParser parser = new InterruptibleRequestParser();
    parser.parse(wrap(head));
    assertFalse(parser.done());
    parser.parse(wrap(tail));
    assertTrue(parser.done());
    assertEquals("POST", parser.getMethod());
    assertEquals("/users/create", parser.getPath());
    assertEquals("HTTP/1.1", parser.getProtocol());
    assertEquals("28", parser.getAttr("Content-Length"));
    assertEquals("first=qqq&last=www&email=eee", parser.getPostString());
  }

}
