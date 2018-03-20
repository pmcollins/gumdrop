package gumdrop.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidatingConverter<T, U> {

  private final List<ConversionField<T, U, ?>> list = new ArrayList<>();

  protected void add(ConversionField<T, U, ?> field) {
    list.add(field);
  }

  public ValidationFailures convert(T t, U u) {
    ValidationFailures validationFailures = new ValidationFailures();
    for (ConversionField<T, U, ?> field : list) {
      Optional<ValidationFailure> optionalFailure = field.convert(t, u);
      optionalFailure.ifPresent(validationFailures::add);
    }
    return validationFailures;
  }

}
