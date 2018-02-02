package gumdrop.test.server;

import gumdrop.common.HttpMethod;
import gumdrop.common.HttpRequest;
import gumdrop.server.nio.LineOrientedRequestParser;
import gumdrop.server.nio.LineReaderDelegate;
import gumdrop.server.nio.RequestBuildingReaderDelegate;
import gumdrop.test.util.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertFalse;
import static gumdrop.test.util.Asserts.assertTrue;

public class LineOrientedRequestParserTest extends Test {

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
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7\r\n" +
    "Referer: http://localhost:8080/users/form\r\n" +
    "Content-Length: 28\r\n" +
    "Accept-Language: en-us\r\n" +
    "\r\n" +
    "first=qqq&last=www&email=eee";

  private static final String PARTIAL_POST_A =
    "POST /users/create HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n" +
    "Content-Type: application/x-www-form-urlencoded\r\n" +
    "Origin: http://localhost:8080\r\n" +
    "Accept-Encoding: gzip, deflate\r\n" +
    "Connection: keep-alive\r\n" +
    "Upgrade-Insecure-Requests: 1\r\n" +
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7\r\n" +
    "Referer: http://localhost:8080/users/form\r\n" +
    "Content-Length: 28\r\n" +
    "Accept-Language: en-us\r\n" +
    "\r\n";

  private static final String PARTIAL_POST_B = "first=qqq&last=www&email=eee";

  private static final String UPLOAD_STR =
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
    "hello!\r\n" +
    "\r\n" +
    "------WebKitFormBoundarynwAxopXoFg6rtPYX--\r\n";
  
  public static void main(String[] args) {
    new LineOrientedRequestParserTest().run();
  }

  @Override
  public void run() {
//    readLine();
//    readLines();
//    twoStageParse();
//    buildGetRequest();
//    buildIncrementalGetRequest();
//    buildPostRequest();
//    twoPartPost();
//    parseQueryString();
//    bbToString();

    upload();
  }

  private void readLine() {
    FakeLineReaderDelegate delegate = new FakeLineReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(GET_STR);
    parser.readLine();
    assertEquals("GET / HTTP/1.1", delegate.getLine(0));
  }

  private void readLines() {
    FakeLineReaderDelegate delegate = new FakeLineReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(GET_STR);
    parser.parse();
    assertEquals(4, delegate.getLineCount());
  }

  private void twoStageParse() {
    FakeLineReaderDelegate oneStageDelegate = new FakeLineReaderDelegate();
    LineOrientedRequestParser oneStageParser = new LineOrientedRequestParser(oneStageDelegate);
    oneStageParser.append(GET_STR);
    oneStageParser.parse();
    assertEquals(4, oneStageDelegate.getLineCount());
    for (int i = 0; i < GET_STR.length(); i++) {
      twoStageParse(i, oneStageDelegate.getLines());
    }
  }

  private void twoStageParse(int splitPt, List<String> lines) {
    FakeLineReaderDelegate delegate = new FakeLineReaderDelegate();
    String firstChunk = GET_STR.substring(0, splitPt);
    String secondChunk = GET_STR.substring(splitPt);
    assertEquals(GET_STR, firstChunk + secondChunk);
    LineOrientedRequestParser twoStageParser = new LineOrientedRequestParser(delegate);
    twoStageParser.append(firstChunk);
    twoStageParser.parse();
    twoStageParser.append(secondChunk);
    twoStageParser.parse();
    assertEquals(lines, delegate.getLines());
  }

  private void buildGetRequest() {
    RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(GET_STR);
    parser.parse();
    HttpRequest request = delegate.getRequest();
    assertEquals(HttpMethod.GET, request.getHttpMethod());
    assertEquals("/", request.getPath());
    assertEquals("HTTP/1.1", request.getProtocol());
    String userAgent = request.getAttr("User-Agent");
    assertEquals("Mozilla", userAgent);
    assertTrue(request.gotBlankLine());
    assertTrue(request.isCompleted());
    assertEquals("localhost:8080", request.getAttr("Host"));
  }

  private void buildIncrementalGetRequest() {
    RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
    int splitPt = 16;
    String firstChunk = GET_STR.substring(0, splitPt);
    String secondChunk = GET_STR.substring(splitPt);
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(firstChunk);
    parser.parse();
    HttpRequest request = delegate.getRequest();
    assertFalse(request.isCompleted());
    parser.append(secondChunk);
    parser.parse();
    assertTrue(request.isCompleted());
  }

  private void buildPostRequest() {
    RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(POST_STR);
    parser.parse();
    HttpRequest request = delegate.getRequest();
    assertEquals(HttpMethod.POST, request.getHttpMethod());
    String postString = request.getPostString();
    assertEquals("first=qqq&last=www&email=eee", postString);
    assertEquals("qqq", request.getParameter("first"));
    assertTrue(request.isCompleted());
  }

  private void twoPartPost() {
    RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(PARTIAL_POST_A);
    parser.parse();
    parser.append(PARTIAL_POST_B);
    parser.parse();
    HttpRequest request = delegate.getRequest();
    assertEquals(HttpMethod.POST, request.getHttpMethod());
    String postString = request.getPostString();
    assertEquals("first=qqq&last=www&email=eee", postString);
  }

  private void parseQueryString() {
    String q = "first=qqq&last=www&email=eee";
    Map<String, String> map = HttpRequest.parseQueryString(q);
    assertEquals("qqq", map.get("first"));
    assertEquals("www", map.get("last"));
    assertEquals("eee", map.get("email"));
  }

  private void bbToString() {
    String s = "hello";
    ByteBuffer bb = ByteBuffer.allocate(1000);
    bb.put(s.getBytes());
    bb.flip();
    String string = LineOrientedRequestParser.bbToString(bb);
    assertEquals(s, string);
  }

  private void upload() {
    RequestBuildingReaderDelegate delegate = new RequestBuildingReaderDelegate();
    LineOrientedRequestParser parser = new LineOrientedRequestParser(delegate);
    parser.append(UPLOAD_STR);
    parser.parse();
  }

  private static class FakeLineReaderDelegate implements LineReaderDelegate {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void line(String line) {
      lines.add(line);
    }

    @Override
    public void endOfChunk(String remainder) {
    }

    int getLineCount() {
      return lines.size();
    }

    List<String> getLines() {
      return lines;
    }

    String getLine(int i) {
      return lines.get(i);
    }

  }

}
