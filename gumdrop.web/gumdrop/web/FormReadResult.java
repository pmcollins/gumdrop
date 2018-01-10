package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

public class FormReadResult<T> {

  private final List<ValidationFailure> failures = new ArrayList<>();
  private T t;

  void addFailure(ValidationFailure failure) {
    failures.add(failure);
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  public List<ValidationFailure> getFailures() {
    return failures;
  }

  public boolean hasFailures() {
    return !failures.isEmpty();
  }

}
