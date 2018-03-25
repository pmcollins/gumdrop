package gumdrop.web.controller;

import gumdrop.common.Session;

public interface ControllerContext<T> {

  Session<T> getSession();

  PathBuilder getPathBuilder(Class<? extends Controller> controllerClass);

  String getPath();

}
