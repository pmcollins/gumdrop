package gumdrop.common;

import java.util.function.BiConsumer;

/**
 * Captures the relationship/binding between a t.uSetter(u) method and a U creator
 */
class SetterBinding<T, U> {

  private final BiConsumer<T, U> fieldSetter;
  private final Creator<U> creator;

  SetterBinding(BiConsumer<T, U> fieldSetter, Creator<U> creator) {
    this.creator = creator;
    this.fieldSetter = fieldSetter;
  }

  CreatedInstance<U> construct(T t) {
    CreatedInstance<U> creator = new CreatedInstance<>(this.creator);
    fieldSetter.accept(t, creator.getObject());
    return creator;
  }

}
