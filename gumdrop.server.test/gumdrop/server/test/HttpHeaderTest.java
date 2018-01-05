package gumdrop.server.test;

import gumdrop.server.HttpResponseHeader;
import gumdrop.test.Test;

import java.io.IOException;

import static gumdrop.server.HttpResponseHeader.EOL;
import static gumdrop.test.TestUtil.assertEquals;

class HttpHeaderTest extends Test {

  public static void main(String[] args) {
    HttpHeaderTest test = new HttpHeaderTest();
    test.run();
  }

  @Override
  public void run() {
    int len = 42;
    String expected = "HTTP/1.1 200 OK" + EOL +
      "Content-Type: text/plain; charset=UTF-8" + EOL +
      "Content-Length: " + len + EOL + EOL;
    HttpResponseHeader httpHeader = new HttpResponseHeader();
    httpHeader.setLength(len);
    FakeOutputStream out = new FakeOutputStream();
    try {
      httpHeader.writeTo(out);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(expected, out.toString());
  }

}
