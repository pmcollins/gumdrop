package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

public class FormReadResult<T> {

  private final List<ValidationFailure> validationFailures = new ArrayList<>();
  private T t;

  void addFailure(ValidationFailure failure) {
    validationFailures.add(failure);
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  public List<ValidationFailure> getValidationFailures() {
    return validationFailures;
  }

  public boolean hasValidationFailures() {
    return !validationFailures.isEmpty();
  }

}
