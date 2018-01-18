package gumdrop.web;

public interface FormReader<T> {

  ReadResult<T> read(String q);

}
