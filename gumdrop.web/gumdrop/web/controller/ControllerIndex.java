package gumdrop.web.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerIndex implements Idx {

  private final Map<Class<? extends Controller>, PathBuilder> map = new HashMap<>();

  public void put(Class<? extends Controller> klass, PathBuilder pathBuilder) {
    map.put(klass, pathBuilder);
  }

  @Override
  public PathBuilder getPathBuilder(Class<? extends Controller> klass) {
    return map.get(klass);
  }

}
