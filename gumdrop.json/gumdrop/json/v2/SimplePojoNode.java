package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SimplePojoNode<T> extends Node<T> {

  private final Function<String, BiConsumer<T, String>> setterMapping;

  protected SimplePojoNode(T t, Function<String, BiConsumer<T, String>> setterMapping) {
    super(t);
    this.setterMapping = setterMapping;
  }

  @Override
  public final Chainable next(String key) {
    BiConsumer<T, String> setter = setterMapping.apply(key);
    return new StringAcceptorNode<>(instance(), setter);
  }

}
