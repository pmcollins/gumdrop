package gumdrop.test.web;

import gumdrop.web.Controller;
import gumdrop.web.PathBuilder;
import gumdrop.web.PathBuilderIndex;

public class FakePathBuilderIndex implements PathBuilderIndex {

  @Override
  public PathBuilder get(Class<? extends Controller> klass) {
    return new PathBuilder(klass.toString());
  }

}
