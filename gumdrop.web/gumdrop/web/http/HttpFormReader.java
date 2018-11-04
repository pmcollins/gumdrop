package gumdrop.web.http;

import gumdrop.common.builder.Builder;
import gumdrop.common.builder.BuilderNode;
import gumdrop.common.validation.ValidationFailure;
import gumdrop.common.validation.Validator;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HttpFormReader<T> implements IFormReader<T> {

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

  protected void addBooleanSetter(Enum<?> e, BiConsumer<T, Boolean> setter) {
    addBooleanSetter(e.toString().toLowerCase(), setter);
  }

  public void addBooleanSetter(String key, BiConsumer<T, Boolean> setter) {
    addSetter(key, (t, s) -> setter.accept(t, Boolean.valueOf(s)));
  }

  public void addIntSetter(Enum<?> e, BiConsumer<T, Integer> setter) {
    addIntSetter(e.toString().toLowerCase(), setter);
  }

  public void addIntSetter(String key, BiConsumer<T, Integer> setter) {
    addSetter(key, (t, s) -> setter.accept(t, Integer.valueOf(s)));
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
    BuilderNode<T> builderNode = new BuilderNode<>(builder);
    String[] pairs = q.split("&");
    FormReadResult<T> out = new FormReadResult<>();
    for (String pair : pairs) {
      Optional<ValidationFailure> validationFailure = parsePair(builderNode, pair);
      validationFailure.ifPresent(out::addFailure);
    }
    out.setT(builderNode.getObject());
    return out;
  }

  private Optional<ValidationFailure> parsePair(BuilderNode<T> builderNode, String pair) {
    int idx = pair.indexOf('=');
    String key = HttpStringUtil.unescape(pair.substring(0, idx));
    String value = HttpStringUtil.unescape(pair.substring(idx + 1));
    builderNode.applyString(key, value);
    Validator<String> validator = validators.get(key);
    return validator == null ? Optional.empty() : validator.validate(value);
  }

}
