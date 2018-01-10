package gumdrop.common;

import java.util.function.BiConsumer;

/**
 * Captures the relationship/binding between a t.uSetter(u) method and a U builder
 */
class SetterBinding<T, U> {

  private final BiConsumer<T, U> fieldSetter;
  private final Builder<U> builder;

  SetterBinding(BiConsumer<T, U> fieldSetter, Builder<U> builder) {
    this.builder = builder;
    this.fieldSetter = fieldSetter;
  }

  BuilderInstance<U> construct(T t) {
    BuilderInstance<U> creator = new BuilderInstance<>(this.builder);
    fieldSetter.accept(t, creator.getObject());
    return creator;
  }

}
