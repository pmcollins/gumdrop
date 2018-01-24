package gumdrop.web;

public interface PathBuilderIndex {

  PathBuilder get(Class<? extends Controller> klass);

}
