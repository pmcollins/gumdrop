package gumdrop.web;

import gumdrop.web.controller.Controller;

public interface PathBuilderIndex {

  PathBuilder get(Class<? extends Controller> klass);

}
