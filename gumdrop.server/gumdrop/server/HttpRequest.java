package gumdrop.server;

import java.util.HashMap;
import java.util.Map;

class HttpRequest {

  private String path;
  private HttpMethod httpMethod;
  private final Map<String, String> headers = new HashMap<>();
  private String protocol;

  HttpMethod getHttpMethod() {
    return httpMethod;
  }

  void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  String getPath() {
    return path;
  }

  void setPath(String path) {
    this.path = path;
  }

  String getProtocol() {
    return protocol;
  }

  void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  void putAttr(String key, String value) {
    headers.put(key, value);
  }

  Map<String, String> getHeaders() {
    return headers;
  }

  String getAttr(String key) {
    return headers.get(key);
  }

}
