package gumdrop.web;

import gumdrop.common.Builder;
import gumdrop.common.BuilderInstance;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FormReader<T> {

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
    BuilderInstance<T> instance = new BuilderInstance<>(builder);
    String[] pairs = q.split("&");
    FormReadResult<T> formReadResult = new FormReadResult<>();
    for (String pair : pairs) {
      parsePair(instance, formReadResult, pair);
    }
    formReadResult.setT(instance.getObject());
    return formReadResult;
  }

  private void parsePair(BuilderInstance<T> instance, FormReadResult<T> formReadResult, String pair) {
    int idx = pair.indexOf('=');
    String key = pair.substring(0, idx);
    String value = pair.substring(idx + 1, pair.length());
    Validator validator = validators.get(key);
    if (validator != null) {
      Optional<ValidationFailure> validationFailure = validator.validate(value);
      validationFailure.ifPresent(formReadResult::addFailure);
    }
    instance.applyString(key, value);
  }

}
