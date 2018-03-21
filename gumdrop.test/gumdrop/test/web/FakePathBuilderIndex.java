package gumdrop.test.web;

import gumdrop.web.control.Controller;
import gumdrop.web.control.PathBuilder;
import gumdrop.web.control.PathBuilderIndex;

public class FakePathBuilderIndex implements PathBuilderIndex {

  @Override
  public PathBuilder get(Class<? extends Controller> klass) {
    return new PathBuilder(klass.toString());
  }

}
