package gumdrop.test.fake;

import gumdrop.json.v1.ListBuilder;

class FullNamePersonListBuilder extends ListBuilder<NamedPerson> {

  FullNamePersonListBuilder() {
    super(new FullNamePersonBuilder());
  }

}
