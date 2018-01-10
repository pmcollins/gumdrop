package gumdrop.web;

import java.util.Optional;
import java.util.function.Function;

public class Validator {

  private final Function<String, Boolean> fcn;
  private final String message;

  public Validator(Function<String, Boolean> fcn, String message) {
    this.fcn = fcn;
    this.message = message;
  }

  public Optional<ValidationFailure> validate(String value) {
    return fcn.apply(value) ? Optional.empty() : Optional.of(new ValidationFailure(message, value));
  }

}
