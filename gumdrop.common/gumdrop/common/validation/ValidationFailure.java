package gumdrop.common.validation;

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
  public String toString() {
    return "ValidationFailure{" +
      "message='" + message + '\'' +
      ", value='" + value + '\'' +
      '}';
  }

}
