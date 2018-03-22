package gumdrop.web.http;

import gumdrop.web.control.ReadResult;

public interface FormReader<T> {

  ReadResult<T> read(String q);

}
