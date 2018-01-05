package gumdrop.server;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

  private final Map<String, String> headers = new HashMap<>();
  private HttpMethod httpMethod;
  private String path;
  private String protocol;

  public HttpRequest(HttpMethod httpMethod, String path) {
    this.httpMethod = httpMethod;
    this.path = path;
  }

  public HttpRequest() {
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getProtocol() {
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

  public String getAttr(String key) {
    return headers.get(key);
  }

}
