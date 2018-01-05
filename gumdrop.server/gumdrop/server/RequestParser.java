package gumdrop.server;

public class RequestParser {

  private final String[] lines;

  public RequestParser(String requestString) {
    lines = requestString.split("\r\n");
  }

  public HttpRequest parse() {
    HttpRequest request = new HttpRequest();
    parseFirstLine(request);
    parseAttributes(request);
    return request;
  }

  private void parseFirstLine(HttpRequest request) {
    String firstLine = lines[0];
    String[] firstLineParts = firstLine.split(" ");
    request.setHttpMethod(HttpMethod.valueOf(firstLineParts[0]));
    request.setPath(firstLineParts[1]);
    request.setProtocol(firstLineParts[2]);
  }

  private void parseAttributes(HttpRequest request) {
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      int idx = line.indexOf(':');
      if (idx > 0) {
        String key = line.substring(0, idx);
        String val = line.substring(idx + 2, line.length());
        request.putAttr(key, val);
      }
    }
  }

}
