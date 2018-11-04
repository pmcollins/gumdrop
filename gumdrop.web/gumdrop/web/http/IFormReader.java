package gumdrop.web.http;

import gumdrop.web.controller.ReadResult;

public interface IFormReader<T> {

  ReadResult<T> read(String q);

}
