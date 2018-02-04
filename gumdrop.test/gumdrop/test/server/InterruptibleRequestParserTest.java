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
    scanMultipart();
  }

  private static final String GET =
    "GET / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "User-Agent: Mozilla\r\n\r\n";

  private static final String POST =
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

  private static final String MULTIPART =
    "POST /prompts/upload HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "Connection: keep-alive\r\n" +
    "Content-Length: 189\r\n" +
    "Pragma: no-cache\r\n" +
    "Cache-Control: no-cache\r\n" +
    "Origin: http://localhost:8080\r\n" +
    "Upgrade-Insecure-Requests: 1\r\n" +
    "Content-Type: multipart/form-data; boundary=----WebKitFormBoundarynwAxopXoFg6rtPYX\r\n" +
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\r\n" +
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
    "Referer: http://localhost:8080/prompts/3\r\n" +
    "Accept-Encoding: gzip, deflate, br\r\n" +
    "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7\r\n" +
    "Cookie: s=89fd9aff-c2b0-4475-bc99-2a2a364698e0\r\n" +
    "\r\n" +
    "------WebKitFormBoundarynwAxopXoFg6rtPYX\r\n" +
    "Content-Disposition: form-data; name=\"foo\"; filename=\"hello.txt\"\r\n" +
    "Content-Type: text/plain\r\n" +
    "\r\n" +
    "hello!\n" +
    "\r\n" +
    "------WebKitFormBoundarynwAxopXoFg6rtPYX--\r\n";

  private static void assertHead(InterruptibleRequestParser parser) {
    assertEquals("GET", parser.getMethod());
    assertEquals("/", parser.getPath());
    assertEquals("HTTP/1.1", parser.getProtocol());
  }

  private static ByteBuffer wrap(String string) {
    byte[] bytes = string.getBytes();
    // emulate a buffer that is bigger than its contents
    ByteBuffer bb = ByteBuffer.allocate(bytes.length * 2);
    return bb.put(bytes).flip();
  }

  private void scanGet() {
    String req = GET;
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
    for (int splitPt = 0; splitPt < POST.length(); splitPt++) {
      assertPost(splitPt);
    }
  }

  private void assertPost(int splitPt) {
    String head = POST.substring(0, splitPt);
    String tail = POST.substring(splitPt, POST.length());
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

  private void scanMultipart() {
    for (int splitPt = 0; splitPt < MULTIPART.length(); splitPt++) {
      String head = MULTIPART.substring(0, splitPt);
      String tail = MULTIPART.substring(splitPt, MULTIPART.length());
      InterruptibleRequestParser parser = new InterruptibleRequestParser();
      parser.parse(wrap(head));
      assertFalse(parser.done());
      parser.parse(wrap(tail));
      assertTrue(parser.done());
    }
  }

}
