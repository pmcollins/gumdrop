package gumdrop.web;

public class ValidationException extends Exception {

  ValidationException(String key, String value) {
    super("key [" + key + "], value: [" + value + "]");
  }

}
