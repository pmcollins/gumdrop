package gumdrop.common.validation;

import java.util.Optional;
import java.util.function.Function;

public class Validator<T> {

  private final Function<T, Boolean> fcn;
  private final String message;

  public Validator(Function<T, Boolean> fcn, String message) {
    this.fcn = fcn;
    this.message = message;
  }

  public Optional<ValidationFailure> validate(String key, T value) {
    return fcn.apply(value)
      ? Optional.empty()
      : Optional.of(new ValidationFailure(key, String.valueOf(value), message));
  }

}
