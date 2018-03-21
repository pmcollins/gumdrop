package gumdrop.web.control;

import gumdrop.common.http.Request;
import gumdrop.web.http.HttpResponse;

public interface Controller {

  HttpResponse process(Request request);

  void setPathArgs(String[] matches);

  String[] getPathArgs();

  void setControllerIndex(ControllerIndex controllerIndex);

}
