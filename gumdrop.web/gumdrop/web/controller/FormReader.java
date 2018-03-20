package gumdrop.web.controller;

public interface FormReader<T> {

  ReadResult<T> read(String q);

}
