package gumdrop.common.builder.v1;

/**
 * Binds a Builder object to an instance whose type corresponds to the Builder
 */
public class BuilderNode<T> {

  private final Builder<T> builder;
  private final T t;

  public BuilderNode(Builder<T> builder) {
    this.builder = builder;
    t = builder.construct();
  }

  public BuilderNode<?> create(String key) {
    SetterBinding<T, ?> setterBinding = builder.getMember(key);
    return setterBinding.constructAndSet(t, key);
  }

  public void applyString(String key, String value) {
    builder.apply(t, key, value);
  }

  public T getObject() {
    return t;
  }

}
