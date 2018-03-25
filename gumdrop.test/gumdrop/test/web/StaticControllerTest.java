package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.controller.StaticController;
import gumdrop.web.http.HttpResponseHeader;

import static gumdrop.test.util.Asserts.assertEquals;

public class StaticControllerTest extends Test {

  public static void main(String[] args) {
    new StaticControllerTest().run();
  }

  @Override
  public void run() {
    StaticController staticController = new StaticController("closed/static");
    staticController.setPathArgs(new String[] {"main.css"});
    HttpResponse response = staticController.process(new FakeHttpRequest());
    HttpResponseHeader header = response.getHeader();
    String status = header.getStatus();
    assertEquals("200 OK", status);
  }

}
