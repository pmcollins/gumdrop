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

  private void parseFirstLine(String line) {
    request = new HttpRequest();
    String[] parts = line.split(" ");
    request.setHttpMethod(HttpMethod.valueOf(parts[0]));
    request.setPath(parts[1]);
    request.setProtocol(parts[2]);
  }

  private void parseLine(String line) {
    if (request.gotBlankLine()) {
      handlePost(line);
    } else {
      int i = line.indexOf(':');
      if (i > 0) {
        String name = line.substring(0, i);
        String value = line.substring(i + 1, line.length());
        request.putAttr(name, value.trim());
      } else if (line.isEmpty()) {
        request.gotBlankLine(true);
      }
    }
  }

  @Override
  public void endOfChunk(String remainder) {
    if (request.getHttpMethod() == HttpMethod.POST) {
      int contentLength = Integer.parseInt(request.getAttr("Content-Length"));
      int remainderLength = remainder.length();
      if (remainderLength == contentLength) {
        handlePost(remainder);
      } else {
        System.out.println("Content length mismatch: expecting [" + contentLength + "], got [" + remainderLength + "]. Waiting for more data.");
      }
    }
  }

  private void handlePost(String line) {
    request.setPostString(line);
    String contentType = request.getAttr("Content-Type");
    if (!contentType.startsWith("multipart/form-data;")) {
      request.writeParameterMap();
    }
  }

  public HttpRequest getRequest() {
    return request;
  }

}
