package gumdrop.web.controller;

import gumdrop.common.http.Request;
import gumdrop.web.http.HttpResponse;

public interface Controller {

  void setControllerIndex(ControllerIndex controllerIndex);

  void setPathArgs(String[] matches);

  String[] getPathArgs();

  HttpResponse process(Request request);

}
