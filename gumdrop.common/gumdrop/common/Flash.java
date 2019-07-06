package gumdrop.common;

import gumdrop.common.validation.ValidationFailures;

import java.util.Objects;

public class Flash {

  private String message;
  private ValidationFailures validationFailures;

  public Flash() {
  }

  public Flash(String message) {
    this(message, null);
  }

  public Flash(ValidationFailures validationFailures) {
    this(null, validationFailures);
  }

  public Flash(String message, ValidationFailures validationFailures) {
    this.message = message;
    this.validationFailures = validationFailures;
  }

  public Flash(Flash other) {
    message = other.message;
    validationFailures = new ValidationFailures(other.validationFailures);
  }

  public ValidationFailures getValidationFailures() {
    return validationFailures;
  }

  public boolean hasValidationFailures() {
    return validationFailures != null && !validationFailures.isEmpty();
  }

  public void setValidationFailures(ValidationFailures validationFailures) {
    this.validationFailures = validationFailures;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Flash flash = (Flash) o;
    return Objects.equals(message, flash.message) &&
      Objects.equals(validationFailures, flash.validationFailures);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, validationFailures);
  }

  @Override
  public String toString() {
    return "Flash{" +
      "message='" + message + '\'' +
      ", validationFailures=" + validationFailures +
      '}';
  }

}
