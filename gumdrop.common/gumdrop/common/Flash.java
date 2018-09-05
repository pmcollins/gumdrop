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

  public Flash(Flash other) {
    message = other.message;
    validationFailures = new ValidationFailures(other.validationFailures);
  }

  public String getMessage() {
    return message;
  }

  public ValidationFailures getValidationFailures() {
    return validationFailures;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "Flash{" +
      "message='" + message + '\'' +
      ", validationFailures=" + validationFailures +
      '}';
  }

}
