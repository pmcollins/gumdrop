package gumdrop.web;

import gumdrop.common.ValidationFailures;

public interface ReadResult<T> {

  T getFormObject();

  ValidationFailures getValidationFailures();

  boolean hasValidationFailures();

}
