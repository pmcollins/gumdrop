package gumdrop.web;

import gumdrop.common.Session;
import gumdrop.web.control.Controller;
import gumdrop.web.control.PathBuilder;

public interface ControllerContext<T> {

  Session<T> getSession();

  PathBuilder getPathBuilder(Class<? extends Controller> controllerClass);

  String getPath();

}
