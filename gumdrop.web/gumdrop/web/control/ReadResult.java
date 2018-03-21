package gumdrop.web.control;

import gumdrop.common.validation.ValidationFailures;

public interface ReadResult<T> {

  T getFormObject();

  ValidationFailures getValidationFailures();

  boolean hasValidationFailures();

}
