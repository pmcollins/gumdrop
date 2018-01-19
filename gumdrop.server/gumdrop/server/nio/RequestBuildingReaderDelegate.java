package gumdrop.server.nio;

import gumdrop.common.HttpMethod;
import gumdrop.common.HttpRequest;

public class RequestBuildingReaderDelegate implements LineReaderDelegate {

  private HttpRequest request;

  @Override
  public void line(String line) {
    if (request == null) {
      parseFirstLine(line);
    } else {
      parseLine(line);
    }
  }

  @Override
  public void endOfDoc(String remainder) {
    if (request.getHttpMethod() == HttpMethod.POST) {
      int contentLength = Integer.parseInt(request.getAttr("Content-Length"));
      int remainderLength = remainder.length();
      if (remainderLength == contentLength) {
        request.setPostString(remainder);
      } else {
        System.err.println("Content length mismatch: expecting [" + contentLength + "], got [" + remainderLength + "]. Waiting for more data.");
      }
    }
  }

  private void parseFirstLine(String line) {
    request = new HttpRequest();
    String[] parts = line.split(" ");
    request.setHttpMethod(HttpMethod.valueOf(parts[0]));
    request.setPath(parts[1]);
    request.setProtocol(parts[2]);
  }

  private void parseLine(String line) {
    int i = line.indexOf(':');
    if (i > 0) {
      String name = line.substring(0, i);
      String value = line.substring(i + 1, line.length());
      request.putAttr(name, value.trim());
    } else if (line.isEmpty()) {
      request.gotBlankLine(true);
    } else if (request.getHttpMethod() == HttpMethod.POST) {
      request.setPostString(line);
    }
  }

  public HttpRequest getRequest() {
    return request;
  }

}
