package gumdrop.test.server;

import gumdrop.common.HttpMethod;
import gumdrop.common.HttpRequest;
import gumdrop.server.nio.HttpReaderDelegate;
import gumdrop.test.util.Test;

import static gumdrop.test.util.Asserts.assertEquals;

public class RequestBuilderDelegateTest extends Test {

  public static void main(String[] args) {
    new RequestBuilderDelegateTest().run();
  }

  @Override
  public void run() {
    post();
  }

  private void post() {
    RequestBuilderDelegate delegate = new RequestBuilderDelegate();
    delegate.word("POST");
    HttpRequest request = delegate.getRequest();
    assertEquals(HttpMethod.POST, request.getHttpMethod());

    delegate.word("/foo");
    assertEquals("/foo", request.getPath());

    delegate.word("HTTP/1.1");
    assertEquals("HTTP/1.1", request.getProtocol());

    delegate.key("User-Agent");
    delegate.value("Mozilla");
    String uaAttr = request.getHeader("User-Agent");
    assertEquals("Mozilla", uaAttr);

    String post = "first=qqq&last=www&email=eee";
    delegate.post(post);
    assertEquals(post, request.getPostString());
  }

}

class RequestBuilderDelegate implements HttpReaderDelegate {

  private final HttpRequest request = new HttpRequest();
  private String key;

  @Override
  public void word(String word) {
    if (request.getHttpMethod() == null) {
      request.setHttpMethod(HttpMethod.valueOf(word));
    } else if (request.getPath() == null) {
      request.setPath(word);
    } else {
      request.setProtocol(word);
    }
  }

  @Override
  public void key(String key) {
    this.key = key;
  }

  @Override
  public void value(String value) {
    request.putAttr(key, value);
  }

  @Override
  public void post(String post) {
    request.setPostString(post);
  }

  public HttpRequest getRequest() {
    return request;
  }

}
