package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.server.nio.MultiParser;
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

  public static void main(String[] args) {
    new MultiParserTest().run();
  }

  @Override
  public void run() {
    dispositionLine();
  }

  private void dispositionLine() {
    String delimStr = "------WebKitFormBoundarynwAxopXoFg6rtPYX";
    CharIterator it = new CharIterator(POST);
    MultiParser multiParser = new MultiParser();
    multiParser.parse(delimStr, it);
    String val = multiParser.getVal();
    assertEquals("hello!\n", val);
  }

}
