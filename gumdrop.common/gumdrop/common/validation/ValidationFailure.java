package gumdrop.common.validation;

import java.util.Objects;

public class ValidationFailure {

  private String key;
  private String message;

  public ValidationFailure() {
  }

  public ValidationFailure(String key, String message) {
    this.key = key;
    this.message = message;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationFailure that = (ValidationFailure) o;
    return Objects.equals(key, that.key) &&
      Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, message);
  }

  @Override
  public String toString() {
    return "ValidationFailure{" +
      "key='" + key + '\'' +
      ", message='" + message + '\'' +
      '}';
  }

}
