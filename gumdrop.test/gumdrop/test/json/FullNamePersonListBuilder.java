package gumdrop.test.json;

import gumdrop.json.ListBuilder;
import gumdrop.test.pojo.Person;

class FullNamePersonListBuilder extends ListBuilder<Person> {

  FullNamePersonListBuilder() {
    super(new FullNamePersonBuilder());
  }

}
