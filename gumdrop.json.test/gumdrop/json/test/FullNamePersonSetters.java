package gumdrop.json.test;

import gumdrop.json.Setters;

class FullNamePersonSetters extends Setters<FullNamePerson> {

  FullNamePersonSetters() {
    super(FullNamePerson::new);
    addIntSetter("age", FullNamePerson::setAge);
    addMember("name", FullNamePerson::setName, new NameSetters());
  }

}
