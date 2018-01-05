package gumdrop.json;

import java.util.function.BiConsumer;

class SetterBinding<T, U> {

  private final BiConsumer<T, U> fieldSetter;
  private final Setters<U> subSetters;

  SetterBinding(BiConsumer<T, U> fieldSetter, Setters<U> subSetters) {
    this.subSetters = subSetters;
    this.fieldSetter = fieldSetter;
  }

  ObjectBuilder<U> construct(T t) {
    ObjectBuilder<U> builder = new ObjectBuilder<>(subSetters);
    fieldSetter.accept(t, builder.getObject());
    return builder;
  }

}
