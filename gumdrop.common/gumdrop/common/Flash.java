package gumdrop.common;

import gumdrop.common.validation.ValidationFailures;

public class Flash {

  private final String message;
  private final ValidationFailures validationFailures;

  public Flash(String message) {
    this(message, null);
  }

  public Flash(ValidationFailures validationFailures) {
    this(null, validationFailures);
  }

  private Flash(String message, ValidationFailures validationFailures) {
    this.message = message;
    this.validationFailures = validationFailures;
  }

  public String getMessage() {
    return message;
  }

  public ValidationFailures getValidationFailures() {
    return validationFailures;
  }

}
