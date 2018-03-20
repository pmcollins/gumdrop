package gumdrop.web.controller;

import gumdrop.common.http.Request;
import gumdrop.web.http.HttpResponse;

public interface Controller {

  HttpResponse process(Request request);

  void setPathArgs(String[] matches);

  String[] getPathArgs();

  void visit(HasControllerIndex hasControllerIndex);

}
