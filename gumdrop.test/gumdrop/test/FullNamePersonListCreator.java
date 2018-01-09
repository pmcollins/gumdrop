package gumdrop.test;

import gumdrop.json.ListCreator;
import gumdrop.test.pojo.FullNamePerson;

class FullNamePersonListCreator extends ListCreator<FullNamePerson> {

  FullNamePersonListCreator() {
    super(new FullNamePersonCreator());
  }

}
