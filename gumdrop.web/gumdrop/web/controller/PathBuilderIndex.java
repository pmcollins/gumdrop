package gumdrop.web.controller;

public interface PathBuilderIndex {

  PathBuilder get(Class<? extends Controller> klass);

}
