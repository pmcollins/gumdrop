package gumdrop.test.fake;

import gumdrop.json.ListBuilder;

class FullNamePersonListBuilder extends ListBuilder<Person> {

  FullNamePersonListBuilder() {
    super(new FullNamePersonBuilder());
  }

}
