package gumdrop.test.fake;

import gumdrop.web.controller.Controller;
import gumdrop.web.controller.PathBuilder;
import gumdrop.web.controller.PathBuilderIndex;

public class FakePathBuilderIndex implements PathBuilderIndex {

  @Override
  public PathBuilder get(Class<? extends Controller> klass) {
    return new PathBuilder(klass.toString());
  }

}
