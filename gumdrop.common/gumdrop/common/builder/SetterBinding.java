package gumdrop.common.builder;

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

  BuilderNode<U> constructAndSet(T t, String key) {
    BuilderNode<U> builderNode = new BuilderNode<>(builder);
    fieldSetter.accept(t, key, builderNode.getObject());
    return builderNode;
  }

  void setNull(T t, String key) {
    fieldSetter.accept(t, key, null);
  }

}
