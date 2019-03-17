package gumdrop.test.fake;

import gumdrop.web.controller.Controller;
import gumdrop.web.controller.PathBuilder;
import gumdrop.web.controller.Idx;

public class FakeIdx implements Idx {

  @Override
  public PathBuilder getPathBuilder(Class<? extends Controller> klass) {
    return new PathBuilder(klass.toString());
  }

}
