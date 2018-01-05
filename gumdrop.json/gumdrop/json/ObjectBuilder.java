package gumdrop.json;

public class ObjectBuilder<T> {

  private final Setters<T> setters;
  private final T t;

  public ObjectBuilder(Setters<T> setters) {
    this.setters = setters;
    t = setters.construct();
  }

  public ObjectBuilder<?> constructMember(String key) {
    SetterBinding<T, ?> memberBinding = setters.getMember(key);
    return memberBinding.construct(t);
  }

  public void applyString(String key, String value) {
    setters.apply(t, key, value);
  }

  public T getObject() {
    return t;
  }

}
