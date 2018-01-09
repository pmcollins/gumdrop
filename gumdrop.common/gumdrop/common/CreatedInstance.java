package gumdrop.common;

public class CreatedInstance<T> {

  private final Creator<T> creator;
  private final T t;

  /**
   * Binds a Creator object to an instance whose type corresponds to the Creator
   */
  public CreatedInstance(Creator<T> creator) {
    this.creator = creator;
    t = creator.construct();
  }

  public CreatedInstance<?> constructMember(String key) {
    SetterBinding<T, ?> memberBinding = creator.getMember(key);
    return memberBinding.construct(t);
  }

  public void applyString(String key, String value) {
    creator.apply(t, key, value);
  }

  public T getObject() {
    return t;
  }

}
