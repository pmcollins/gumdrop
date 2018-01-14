package gumdrop.web;

import gumdrop.common.Builder;
import gumdrop.common.InstanceBuilder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FormReader<T> implements IFormReader<T> {

  private final Builder<T> builder;
  private final Map<String, Validator> validators = new HashMap<>();

  public FormReader(Supplier<T> constructor) {
    this.builder = new Builder<>(constructor);
  }

  public void addSetter(String key, BiConsumer<T, String> setter) {
    builder.addSetter(key, setter);
  }

  public void addSetter(String key, BiConsumer<T, String> setter, Validator validator) {
    addSetter(key, setter);
    validators.put(key, validator);
  }

  public FormReadResult<T> read(String q) {
    InstanceBuilder<T> instance = new InstanceBuilder<>(builder);
    String[] pairs = q.split("&");
    FormReadResult<T> out = new FormReadResult<>();
    for (String pair : pairs) {
      Optional<ValidationFailure> validationFailure = parsePair(instance, pair);
      validationFailure.ifPresent(out::addFailure);
    }
    out.setT(instance.getObject());
    return out;
  }

  private Optional<ValidationFailure> parsePair(InstanceBuilder<T> instance, String pair) {
    int idx = pair.indexOf('=');
    String key = pair.substring(0, idx);
    String value = pair.substring(idx + 1, pair.length());
    Validator validator = validators.get(key);
    instance.applyString(key, value);
    return validator == null ? Optional.empty() : validator.validate(value);
  }

}
