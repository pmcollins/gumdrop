package gumdrop.web;

public interface IFormReader<T> {
  FormReadResult<T> read(String q);
}
