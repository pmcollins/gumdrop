package gumdrop.web.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseHeader {

  private String protocol;
  private String status;
  private final Map<String, String> attrs = new LinkedHashMap<>();

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void putAttr(String key, String val) {
    String prev = attrs.put(key, val);
    if (prev != null) {
      // TODO support multiple values with the same key
      throw new RuntimeException("double set of key: " + prev);
    }
  }

  public void setCookie(String key, String val) {
    putAttr("Set-Cookie", key + "=" + val);
  }

  public String getProtocol() {
    return protocol;
  }

  public String getStatus() {
    return status;
  }

  public Map<String, String> getAttrs() {
    return attrs;
  }

  @Override
  public String toString() {
    return "HttpResponseHeader{" +
      "protocol='" + protocol + '\'' +
      ", status='" + status + '\'' +
      ", attrs=" + attrs +
      '}';
  }

}
