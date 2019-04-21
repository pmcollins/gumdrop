package gumdrop.web.http;

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

  public static void setHtmlResponseHeaders(HttpResponseHeader responseHeader, int contentLength) {
    setOkHeaders(responseHeader, contentLength, "text/html");
  }

  public static void setTextCssHeaders(HttpResponseHeader responseHeader, int contentLength) {
    setOkHeaders(responseHeader, contentLength, "text/css");
  }

  public static void setImageSvgHeaders(HttpResponseHeader responseHeader, int contentLength) {
    setOkHeaders(responseHeader, contentLength, "image/svg+xml");
  }

  public static void setOkHeaders(HttpResponseHeader responseHeader, int contentLength, String type) {
    setHttp11ProtocolHeaders(responseHeader);
    responseHeader.setStatus("200 OK");
    responseHeader.putAttr("Content-Type", type);
    responseHeader.putAttr("Content-Length", String.valueOf(contentLength));
  }

  public static void setRedirectHeaders(HttpResponseHeader responseHeader, String redirectTarget) {
    setHttp11ProtocolHeaders(responseHeader);
    responseHeader.setStatus("303 See Other");
    responseHeader.putAttr("Location", redirectTarget);
  }

  private static void setHttp11ProtocolHeaders(HttpResponseHeader responseHeader) {
    responseHeader.setProtocol("HTTP/1.1");
  }

}
