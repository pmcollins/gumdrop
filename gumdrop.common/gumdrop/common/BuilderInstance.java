package gumdrop.common;

public class BuilderInstance<T> {

  private final Builder<T> builder;
  private final T t;

  /**
   * Binds a Builder object to an instance whose type corresponds to the Builder
   */
  public BuilderInstance(Builder<T> builder) {
    this.builder = builder;
    t = builder.construct();
  }

  public BuilderInstance<?> constructMember(String key) {
    SetterBinding<T, ?> memberBinding = builder.getMember(key);
    return memberBinding.construct(t);
  }

  public void applyString(String key, String value) {
    builder.apply(t, key, value);
  }

  public T getObject() {
    return t;
  }

}
