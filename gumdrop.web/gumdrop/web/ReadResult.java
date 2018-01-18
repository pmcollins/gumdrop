package gumdrop.web;

import gumdrop.common.ValidationFailure;

import java.util.List;

public interface ReadResult<T> {

  T getFormObject();

  List<ValidationFailure> getValidationFailures();

  boolean hasValidationFailures();

}
