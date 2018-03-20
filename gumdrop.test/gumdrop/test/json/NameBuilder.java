package gumdrop.test.json;

import gumdrop.common.builder.Builder;
import gumdrop.test.pojo.Name;

class NameBuilder extends Builder<Name> {

  NameBuilder() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
