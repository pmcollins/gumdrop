package gumdrop.test;

import gumdrop.common.Builder;
import gumdrop.test.pojo.Name;

class NameBuilder extends Builder<Name> {

  NameBuilder() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
