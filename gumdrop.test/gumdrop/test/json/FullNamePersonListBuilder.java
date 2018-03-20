package gumdrop.test.json;

import gumdrop.json.ListBuilder;
import gumdrop.test.pojo.FullNamePerson;

class FullNamePersonListBuilder extends ListBuilder<FullNamePerson> {

  FullNamePersonListBuilder() {
    super(new FullNamePersonBuilder());
  }

}
