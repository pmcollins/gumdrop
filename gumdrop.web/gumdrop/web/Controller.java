package gumdrop.web;

import gumdrop.common.Request;

public interface Controller {

  Response process(Request request);

  void setPathArgs(String[] matches);

  void visit(HasControllerIndex hasControllerIndex);

}
