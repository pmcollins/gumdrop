package gumdrop.test.json;

import gumdrop.json.Setters;

class NameSetters extends Setters<Name> {

  NameSetters() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
