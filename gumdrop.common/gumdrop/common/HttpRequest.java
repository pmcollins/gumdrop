package gumdrop.common;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {

  private final Map<String, String> headers = new HashMap<>();
  private HttpMethod httpMethod;
  private String path;
  private String protocol;
  private boolean gotBlankLine;
  private String postString;
  private Map<String, String> parameterMap;

  public HttpRequest(HttpMethod httpMethod, String path) {
    this.httpMethod = httpMethod;
    this.path = path;
  }

  public HttpRequest() {
  }

  public static Map<String, String> parseQueryString(String q) {
    Map<String, String> out = new HashMap<>();
    String[] pairs = q.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      String key = pair.substring(0, idx);
      String value = pair.substring(idx + 1, pair.length());
      out.put(key, value);
    }
    return out;
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

  Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  public String getAttr(String key) {
    return headers.get(key);
  }

  public boolean gotBlankLine() {
    return gotBlankLine;
  }

  public void gotBlankLine(boolean b) {
    gotBlankLine = b;
  }

  public boolean isCompleted() {
    // postString is only set by RequestBuildingReaderDelegate when all data has been accumulated
    // and Content-Length matches postString length
    return (httpMethod == HttpMethod.GET && gotBlankLine) || (httpMethod == HttpMethod.POST && postString != null);
  }

  @Override
  public String getPostString() {
    return postString;
  }

  public void setPostString(String postString) {
    this.postString = postString;
    this.parameterMap = parseQueryString(postString);
  }

  public String getParameter(String key) {
    return parameterMap.get(key);
  }

  @Override
  public String getCookieString() {
    return getAttr("Cookie");
  }

  @Override
  public String toString() {
    return "HttpRequest{" +
      "headers=" + headers +
      ", httpMethod=" + httpMethod +
      ", path='" + path + '\'' +
      ", protocol='" + protocol + '\'' +
      ", gotBlankLine=" + gotBlankLine +
      ", postString='" + postString + '\'' +
      ", parameterMap=" + parameterMap +
      '}';
  }

}
