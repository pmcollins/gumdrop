package gumdrop.web.http;

import gumdrop.common.builder.Builder;
import gumdrop.common.builder.GraphBuilder;
import gumdrop.common.validation.ValidationFailure;
import gumdrop.common.validation.Validator;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HttpFormReader<T> implements FormReader<T> {

  private final Builder<T> builder;
  private final QueryValidatorMap validators = new QueryValidatorMap();

  public HttpFormReader(Supplier<T> constructor) {
    this.builder = new Builder<>(constructor);
  }

  protected void addSetter(Enum<?> e, BiConsumer<T, String> setter) {
    addSetter(e.toString().toLowerCase(), setter);
  }

  protected void addSetter(Enum<?> e, BiConsumer<T, String> setter, Validator<String> validator) {
    addSetter(e.toString().toLowerCase(), setter, validator);
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
    GraphBuilder<T> graphBuilder = new GraphBuilder<>(builder);
    String[] pairs = q.split("&");
    FormReadResult<T> out = new FormReadResult<>();
    for (String pair : pairs) {
      Optional<ValidationFailure> validationFailure = parsePair(graphBuilder, pair);
      validationFailure.ifPresent(out::addFailure);
    }
    out.setT(graphBuilder.getObject());
    return out;
  }

  private Optional<ValidationFailure> parsePair(GraphBuilder<T> graphBuilder, String pair) {
    int idx = pair.indexOf('=');
    String key = HttpStringUtil.unescape(pair.substring(0, idx));
    String value = HttpStringUtil.unescape(pair.substring(idx + 1, pair.length()));
    graphBuilder.applyString(key, value);
    Validator<String> validator = validators.get(key);
    return validator == null ? Optional.empty() : validator.validate(value);
  }

}
