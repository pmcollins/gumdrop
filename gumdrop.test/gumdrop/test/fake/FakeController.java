package gumdrop.test.fake;

import gumdrop.common.http.Request;
import gumdrop.web.controller.Controller;
import gumdrop.web.controller.ControllerIndex;
import gumdrop.web.http.HttpResponse;

public class FakeController implements Controller {

  private String[] matches;

  @Override
  public HttpResponse process(Request request) {
    return null;
  }

  @Override
  public void setPathArgs(String[] matches) {
    this.matches = matches;
  }

  @Override
  public String[] getPathArgs() {
    return matches;
  }

  @Override
  public void setControllerIndex(ControllerIndex controllerIndex) {
  }

}
