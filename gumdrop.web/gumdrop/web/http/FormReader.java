package gumdrop.web.http;

import gumdrop.web.controller.ReadResult;

public interface FormReader<T> {

  ReadResult<T> read(String q);

}
