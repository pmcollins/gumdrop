package gumdrop.json;

import gumdrop.common.builder.Builder;

class HolderBuilder<T> extends Builder<Holder<T>> {

  static final String HOLDER_SET_KEY = "set";

  HolderBuilder(Builder<T> subBuilder) {
    super(Holder::new);
    addMember(HOLDER_SET_KEY, Holder::setContents, subBuilder);
  }

}
