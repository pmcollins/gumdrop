package gumdrop.server;

import java.util.LinkedHashMap;
import java.util.Map;

class HttpResponseHeaderData {

  private String protocol;
  private String status;
  private final Map<String, String> attrs = new LinkedHashMap<>();

  void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  void setStatus(String status) {
    this.status = status;
  }

  void putAttr(String key, String val) {
    attrs.put(key, val);
  }

  String getProtocol() {
    return protocol;
  }

  String getStatus() {
    return status;
  }

  Map<String, String> getAttrs() {
    return attrs;
  }

}
