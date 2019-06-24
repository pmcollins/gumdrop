package gumdrop.test.fake;

import gumdrop.common.builder.v1.Builder;

class NameBuilder extends Builder<Name> {

  NameBuilder() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
