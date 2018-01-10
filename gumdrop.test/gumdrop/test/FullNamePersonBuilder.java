package gumdrop.test;

import gumdrop.common.Builder;
import gumdrop.test.pojo.FullNamePerson;

class FullNamePersonBuilder extends Builder<FullNamePerson> {

  FullNamePersonBuilder() {
    super(FullNamePerson::new);
    addIntSetter("age", FullNamePerson::setAge);
    addMember("name", FullNamePerson::setName, new NameBuilder());
  }

}
