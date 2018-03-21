package gumdrop.web.control;

public interface FormReader<T> {

  ReadResult<T> read(String q);

}
