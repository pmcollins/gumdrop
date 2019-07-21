package gumdrop.json;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PojoNode<T> extends NullableNode<T> {

  private final Supplier<T> constructor;
  private final List<FieldBinding<T, ?>> bindings;

  public PojoNode(Supplier<T> constructor, List<FieldBinding<T, ?>> bindings) {
    this(constructor, bindings, null);
  }

  public PojoNode(Supplier<T> constructor, List<FieldBinding<T, ?>> bindings, Consumer<T> listener) {
    super(listener);
    this.constructor = constructor;
    this.bindings = bindings;
  }

  @Override
  public Chainable next() {
    T t = constructor.get();
    setValue(t);
    return new PojoAttributesNode<>(t, bindings);
  }

}
