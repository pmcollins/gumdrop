package gumdrop.web;

import gumdrop.common.http.Request;

public interface Controller {

  HttpResponse process(Request request);

  void setPathArgs(String[] matches);

  String[] getPathArgs();

  void visit(HasControllerIndex hasControllerIndex);

}
