package gumdrop.web;

import gumdrop.common.Request;

public interface Controller {

  HttpResponse process(Request request);

  void setPathArgs(String[] matches);

  void visit(HasControllerIndex hasControllerIndex);

}
