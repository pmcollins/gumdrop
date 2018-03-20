package gumdrop.test.server;

import gumdrop.common.builder.ByteIterator;
import gumdrop.web.http.MultiPartParser;
import gumdrop.test.util.Test;

import static gumdrop.test.util.Asserts.assertEquals;

public class MultiParserTest extends Test {

  private static final String POST =
    "------WebKitFormBoundarynwAxopXoFg6rtPYX\r\n" +
    "Content-Disposition: form-data; name=\"foo\"; filename=\"hello.txt\"\r\n" +
    "Content-Type: text/plain\r\n" +
    "\r\n" +
    "hello!\n" +
    "\r\n" +
    "------WebKitFormBoundarynwAxopXoFg6rtPYX--\r\n";

  private static final String MULTI = "Content-Type: multipart/form-data; boundary=----WebKitFormBoundarynwAxopXoFg6rtPYX";

  public static void main(String[] args) {
    new MultiParserTest().run();
  }

  @Override
  public void run() {
    parseBoundary();
    parseSinglePart();
  }

  private void parseBoundary() {
    String boundary = MultiPartParser.parseMultipartHeader(MULTI);
    assertEquals("----WebKitFormBoundarynwAxopXoFg6rtPYX", boundary);
  }

  private void parseSinglePart() {
    String delimStr = "----WebKitFormBoundarynwAxopXoFg6rtPYX";
    ByteIterator it = new ByteIterator(POST);
    byte[] bytes = MultiPartParser.parseSinglePart(delimStr, it);
    assertEquals("hello!\n", new String(bytes));
  }

}
