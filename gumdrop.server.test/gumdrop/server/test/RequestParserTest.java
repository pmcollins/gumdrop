package gumdrop.server.test;

import gumdrop.server.HttpMethod;
import gumdrop.server.HttpRequest;
import gumdrop.server.RequestParser;
import gumdrop.test.Test;

import static gumdrop.test.TestUtil.assertEquals;

class RequestParserTest extends Test {

  public static void main(String[] args) {
    RequestParserTest test = new RequestParserTest();
    test.run();
  }

  @Override
  public void run() {
    //noinspection SpellCheckingInspection
    String requestStr = "GET / HTTP/1.1\r\n" +
      "Host: localhost:8080\r\n" +
      "Connection: keep-alive\r\n" +
      "Cache-Control: max-age=0\r\n" +
      "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\r\n" +
      "Upgrade-Insecure-Requests: 1\r\n" +
      "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
      "Accept-Encoding: gzip, deflate, br\r\n" +
      "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7\r\n\r\n";
    RequestParser requestParser = new RequestParser(requestStr);
    HttpRequest request = requestParser.parse();
    assertEquals(HttpMethod.GET, request.getHttpMethod());
    assertEquals("/", request.getPath());
    assertEquals("HTTP/1.1", request.getProtocol());
    assertEquals("localhost:8080", request.getAttr("Host"));
    assertEquals("en-US,en;q=0.9,es;q=0.8,pt;q=0.7", request.getAttr("Accept-Language"));
  }

}
