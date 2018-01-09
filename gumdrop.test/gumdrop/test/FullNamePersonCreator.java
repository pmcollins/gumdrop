package gumdrop.test;

import gumdrop.common.Creator;
import gumdrop.test.pojo.FullNamePerson;

class FullNamePersonCreator extends Creator<FullNamePerson> {

  FullNamePersonCreator() {
    super(FullNamePerson::new);
    addIntSetter("age", FullNamePerson::setAge);
    addMember("name", FullNamePerson::setName, new NameCreator());
  }

}
