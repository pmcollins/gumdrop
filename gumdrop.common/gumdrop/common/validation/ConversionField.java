package gumdrop.common.validation;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ConversionField<T, U, V> {

  private final Function<T, V> getter;
  private final BiConsumer<U, V> setter;
  private final Validator<V> validator;

  public ConversionField(Function<T, V> getter, BiConsumer<U, V> setter) {
    this(getter, setter, null);
  }

  public ConversionField(Function<T, V> getter, BiConsumer<U, V> setter, Validator<V> validator) {
    this.getter = getter;
    this.setter = setter;
    this.validator = validator;
  }

  Optional<ValidationFailure> convert(T t, U u) {
    V v = getter.apply(t);
    setter.accept(u, v);
    return validator != null ? validator.validate(v) : Optional.empty();
  }

}
