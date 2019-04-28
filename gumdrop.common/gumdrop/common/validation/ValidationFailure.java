package gumdrop.common.validation;

import java.util.Objects;

public class ValidationFailure {

  private final String key;
  private final String value;
  private final String message;

  public ValidationFailure(String key, String value, String message) {
    this.key = key;
    this.value = value;
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public String getMessage() {
    return message;
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
      "key='" + key + '\'' +
      ", value='" + value + '\'' +
      ", message='" + message + '\'' +
      '}';
  }

}
