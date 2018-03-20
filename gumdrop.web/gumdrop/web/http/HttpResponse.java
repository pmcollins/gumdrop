package gumdrop.web.http;

public class HttpResponse {

  private final HttpResponseHeader header;
  private byte[] bytes;

  public HttpResponse(HttpResponseHeader header) {
    this.header = header;
  }

  public HttpResponseHeader getHeader() {
    return header;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public String toString() {
    String s = bytes == null ? null : new String(bytes);
    return "Response{" +
      "header=" + header +
      ", bytes=" + s +
      '}';
  }

}
