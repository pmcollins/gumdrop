package gumdrop.test.fake;

import gumdrop.json.ListBuilder;

class FullNamePersonListBuilder extends ListBuilder<NamedPerson> {

  FullNamePersonListBuilder() {
    super(new FullNamePersonBuilder());
  }

}
