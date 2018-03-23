package gumdrop.common.builder;

/**
 * Binds a Builder object to an instance whose type corresponds to the Builder
 */
public class GraphBuilder<T> {

  private final Builder<T> builder;
  private final T t;

  public GraphBuilder(Builder<T> builder) {
    this.builder = builder;
    t = builder.construct();
  }

  public GraphBuilder<?> create(String key) {
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
