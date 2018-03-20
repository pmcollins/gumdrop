package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.controller.StaticController;

public class StaticControllerTest extends Test {

  public static void main(String[] args) {
    new StaticControllerTest().run();
  }

  @Override
  public void run() {
    StaticController staticController = new StaticController("closed/static");
    staticController.setPathArgs(new String[] {"main.css"});
    HttpResponse response = staticController.process(new FakeHttpRequest());
  }

}
