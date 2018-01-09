package gumdrop.json;

import gumdrop.common.Creator;

class HolderCreator<T> extends Creator<Holder<T>> {

  static final String HOLDER_SET_KEY = "set";

  HolderCreator(Creator<T> subCreator) {
    super(Holder::new);
    addMember(HOLDER_SET_KEY, Holder::setContents, subCreator);
  }

}
