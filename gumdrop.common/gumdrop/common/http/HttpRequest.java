package gumdrop.common.http;

import java.util.Map;

public class HttpRequest implements Request {

  private Map<String, String> headers;
  private HttpMethod httpMethod;
  private String path;
  private String protocol;
  private byte[] post;
  private Map<String, String> parameterMap;

  public HttpRequest(HttpMethod httpMethod, String path) {
    this.httpMethod = httpMethod;
    this.path = path;
  }

  public HttpRequest() {
  }

  public void setHeaders(Map<String, String> map) {
    headers = map;
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  @Override
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public void putAttr(String key, String value) {
    headers.put(key, value);
  }

  @Override
  public String getCookieString() {
    return getHeader("Cookie");
  }

  @Override
  public String getHeader(String key) {
    return headers.get(key);
  }

  @Override
  public String getPostString() {
    return new String(post);
  }

  public void setPost(byte[] post) {
    this.post = post;
  }

  @Override
  public byte[] getPost() {
    return post;
  }

  public boolean isPost() {
    return httpMethod == HttpMethod.POST;
  }

  public void setParameterMap(Map<String, String> parameterMap) {
    this.parameterMap = parameterMap;
  }

  @Override
  public String toString() {
    return "HttpRequest{" +
      "headers=" + headers +
      ", httpMethod=" + httpMethod +
      ", path='" + path + '\'' +
      ", protocol='" + protocol + '\'' +
      ", parameterMap=" + parameterMap +
      '}';
  }

}
