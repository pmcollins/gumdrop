package gumdrop.common.validation;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ConversionField<T, U, V> {

  private final String name;
  private final Function<T, V> getter;
  private final BiConsumer<U, V> setter;
  private final Validator<V> validator;

  public ConversionField(String name, Function<T, V> getter, BiConsumer<U, V> setter) {
    this(name, getter, setter, null);
  }

  public ConversionField(String name, Function<T, V> getter, BiConsumer<U, V> setter, Validator<V> validator) {
    this.name = name;
    this.getter = getter;
    this.setter = setter;
    this.validator = validator;
  }

  Optional<ValidationFailure> convert(T t, U u) {
    V v = getter.apply(t);
    setter.accept(u, v);
    return validator != null ? validator.validate(name, v) : Optional.empty();
  }

}
