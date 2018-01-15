package gumdrop.web;

public interface FormReader<T> {

  FormReadResult<T> read(String q);

}
