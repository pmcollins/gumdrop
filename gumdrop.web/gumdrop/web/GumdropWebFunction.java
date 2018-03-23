package gumdrop.web;

import gumdrop.common.http.HttpRequest;
import gumdrop.web.control.Dispatcher;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.util.Arrays;
import java.util.function.Function;

public class GumdropWebFunction implements Function<HttpRequest, byte[]> {

  private final Dispatcher dispatcher;

  public GumdropWebFunction(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @Override
  public byte[] apply(HttpRequest request) {
    HttpResponse response;
    try {
      response = dispatcher.processRequest(request);
    } catch (Exception e) {
      e.printStackTrace();
      response = dispatcher.processError(request);
    }
    HttpResponseHeader header = response.getHeader();
    byte[] headerBytes = HeaderUtil.buildString(header).getBytes();
    byte[] bodyBytes = response.getBytes();
    if (bodyBytes == null) {
      return headerBytes;
    } else {
      byte[] out = Arrays.copyOf(headerBytes, headerBytes.length + bodyBytes.length);
      System.arraycopy(bodyBytes, 0, out, headerBytes.length, bodyBytes.length);
      return out;
    }
  }

}
