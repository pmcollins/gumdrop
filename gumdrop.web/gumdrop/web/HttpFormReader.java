package gumdrop.web;

import gumdrop.common.Builder;
import gumdrop.common.InstanceBuilder;
import gumdrop.common.ValidationFailure;
import gumdrop.common.Validator;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HttpFormReader<T> implements FormReader<T> {

  private final Builder<T> builder;
  private final QueryValidatorMap validators = new QueryValidatorMap();

  public HttpFormReader(Supplier<T> constructor) {
    this.builder = new Builder<>(constructor);
  }

  public void addSetter(String key, BiConsumer<T, String> setter) {
    builder.addSetter(key, setter);
  }

  public void addSetter(String key, BiConsumer<T, String> setter, Validator<String> validator) {
    addSetter(key, setter);
    validators.put(key, validator);
  }

  @Override
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
    String key = HtmlEscapist.unescape(pair.substring(0, idx));
    String value = HtmlEscapist.unescape(pair.substring(idx + 1, pair.length()));
    Validator<String> validator = validators.get(key);
    instance.applyString(key, value);
    return validator == null ? Optional.empty() : validator.validate(value);
  }

}
