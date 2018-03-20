package gumdrop.web.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerIndex implements PathBuilderIndex {

  private final Map<Class<? extends Controller>, PathBuilder> map = new HashMap<>();

  public void put(Class<? extends Controller> klass, PathBuilder pathBuilder) {
    PathBuilder put = map.put(klass, pathBuilder);
    if (put != null) {
      throw new RuntimeException("multiple classes per path not supported yet");
    }
  }

  @Override
  public PathBuilder get(Class<? extends Controller> klass) {
    return map.get(klass);
  }

}
