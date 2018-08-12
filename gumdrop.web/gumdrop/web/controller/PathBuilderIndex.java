package gumdrop.web.controller;

public interface PathBuilderIndex {

  PathBuilder getPathBuilder(Class<? extends Controller> klass);

}
