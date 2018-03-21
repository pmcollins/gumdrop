package gumdrop.web.http;

import gumdrop.common.validation.ValidationFailure;
import gumdrop.common.validation.ValidationFailures;
import gumdrop.web.control.ReadResult;

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
