package gumdrop.test.web;

import gumdrop.test.fake.FakeHttpRequest;
import gumdrop.test.util.Test;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.controller.StaticController;
import gumdrop.web.http.HttpResponseHeader;

import java.util.Set;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertThrows;

public class StaticControllerTest extends Test {

  public static void main(String[] args) {
    new StaticControllerTest().run();
  }

  @Override
  public void run() {
    ok();
    invalidExtension();
  }

  private void ok() {
    int maxAgeSeconds = 600;
    StaticController staticController = new StaticController("closed/static", Set.of("css"), maxAgeSeconds);
    staticController.setPathArgs(new String[] {"grid.css"});
    HttpResponse response = staticController.process(new FakeHttpRequest());
    HttpResponseHeader header = response.getHeader();
    String status = header.getStatus();
    assertEquals("200 OK", status);
    String cacheControl = header.getAttrs().get("Cache-Control"); // Cache-Control: public, max-age=12345
    assertEquals("public, max-age=" + maxAgeSeconds, cacheControl);
  }

  private void invalidExtension() {
    StaticController staticController = new StaticController("closed/static", Set.of("xyz"), 600);
    staticController.setPathArgs(new String[] {"grid.css"});
    assertThrows(() -> staticController.process(new FakeHttpRequest()), RuntimeException.class);
  }

}
