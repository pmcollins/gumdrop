package gumdrop.web;

import gumdrop.web.http.HttpResponseHeader;

import java.util.Map;

public class HeaderUtil {

  private static final String EOL = "\r\n";

  private HeaderUtil() {
  }

  public static String buildString(HttpResponseHeader h) {
    StringBuilder sb = new StringBuilder(1024);
    sb.append(h.getProtocol()).append(" ").append(h.getStatus()).append(EOL);
    for (Map.Entry<String, String> e : h.getAttrs().entrySet()) {
      sb.append(e.getKey()).append(": ").append(e.getValue()).append(EOL);
    }
    sb.append(EOL);
    return sb.toString();
  }

  private static void setProtocol(HttpResponseHeader responseHeader) {
    responseHeader.setProtocol("HTTP/1.1");
  }

  public static void setHtmlResponseType(HttpResponseHeader responseHeader, int contentLength) {
    setType(responseHeader, contentLength, "text/html");
  }

  public static void setTextCss(HttpResponseHeader responseHeader, int contentLength) {
    setType(responseHeader, contentLength, "text/css");
  }

  private static void setType(HttpResponseHeader responseHeader, int contentLength, String type) {
    setProtocol(responseHeader);
    responseHeader.setStatus("200 OK");
    responseHeader.putAttr("Content-Type", type);
    responseHeader.putAttr("Content-Length", String.valueOf(contentLength));
  }

  public static void setRedirect(HttpResponseHeader responseHeader, String redirectTarget) {
    setProtocol(responseHeader);
    responseHeader.setStatus("303 See Other");
    responseHeader.putAttr("Location", redirectTarget);
  }

}
