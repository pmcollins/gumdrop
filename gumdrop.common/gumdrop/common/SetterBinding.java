package gumdrop.common;

import java.util.function.BiConsumer;

/**
 * Captures the relationship/binding between a t.uSetter(u) method and a U builder
 */
class SetterBinding<T, U> {

  private final TriConsumer<T, U> fieldSetter;
  private final Builder<U> builder;

  SetterBinding(BiConsumer<T, U> fieldSetter, Builder<U> builder) {
    this.builder = builder;
    this.fieldSetter = (t, s, u) -> fieldSetter.accept(t, u);
  }

  SetterBinding(TriConsumer<T, U> fieldSetter, Builder<U> builder) {
    this.builder = builder;
    this.fieldSetter = fieldSetter;
  }

  InstanceBuilder<U> constructAndSet(T t, String key) {
    InstanceBuilder<U> instanceBuilder = new InstanceBuilder<>(builder);
    fieldSetter.accept(t, key, instanceBuilder.getObject());
    return instanceBuilder;
  }

}
