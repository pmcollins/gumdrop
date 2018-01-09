package gumdrop.test;

import gumdrop.common.Creator;
import gumdrop.test.pojo.Name;

class NameCreator extends Creator<Name> {

  NameCreator() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
