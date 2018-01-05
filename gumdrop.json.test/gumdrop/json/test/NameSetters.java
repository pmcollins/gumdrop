package gumdrop.json.test;

import gumdrop.json.Setters;

class NameSetters extends Setters<Name> {

  NameSetters() {
    super(Name::new);
    addSetter("first", Name::setFirst);
    addSetter("last", Name::setLast);
  }

}
