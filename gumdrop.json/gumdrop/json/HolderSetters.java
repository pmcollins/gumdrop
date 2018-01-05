package gumdrop.json;

class HolderSetters<T> extends Setters<Holder<T>> {

  static final String HOLDER_SET_KEY = "set";

  HolderSetters(Setters<T> subSetters) {
    super(Holder::new);
    addMember(HOLDER_SET_KEY, Holder::setContents, subSetters);
  }

}
