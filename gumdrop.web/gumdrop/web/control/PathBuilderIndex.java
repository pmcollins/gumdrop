package gumdrop.web.control;

public interface PathBuilderIndex {

  PathBuilder get(Class<? extends Controller> klass);

}
