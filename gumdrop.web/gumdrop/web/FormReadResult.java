package gumdrop.web;

import gumdrop.common.ValidationFailure;
import gumdrop.common.ValidationFailures;

public class FormReadResult<T> implements ReadResult<T> {

  private final ValidationFailures vf = new ValidationFailures();
  private T t;

  void addFailure(ValidationFailure failure) {
    vf.add(failure);
  }

  @Override
  public T getFormObject() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  @Override
  public ValidationFailures getValidationFailures() {
    return vf;
  }

  @Override
  public boolean hasValidationFailures() {
    return !vf.isEmpty();
  }

}
