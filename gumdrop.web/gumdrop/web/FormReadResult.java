package gumdrop.web;

import gumdrop.common.ValidationFailure;

import java.util.ArrayList;
import java.util.List;

public class FormReadResult<T> implements ReadResult<T> {

  private final List<ValidationFailure> validationFailures = new ArrayList<>();
  private T t;

  void addFailure(ValidationFailure failure) {
    validationFailures.add(failure);
  }

  @Override
  public T getFormObject() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  @Override
  public List<ValidationFailure> getValidationFailures() {
    return validationFailures;
  }

  @Override
  public boolean hasValidationFailures() {
    return !validationFailures.isEmpty();
  }

}
