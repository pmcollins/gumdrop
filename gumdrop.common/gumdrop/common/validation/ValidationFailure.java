package gumdrop.common.validation;

import java.util.Objects;

public class ValidationFailure {

  private final String message, value;

  ValidationFailure(String message, String value) {
    this.message = message;
    this.value = value;
  }

  public String getMessage() {
    return message;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationFailure that = (ValidationFailure) o;
    return Objects.equals(message, that.message) &&
      Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, value);
  }

  @Override
  public String toString() {
    return "ValidationFailure{" +
      "message='" + message + '\'' +
      ", value='" + value + '\'' +
      '}';
  }

}
