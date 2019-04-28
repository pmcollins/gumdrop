package gumdrop.test.fake;

import gumdrop.common.builder.Builder;

class FullNamePersonBuilder extends Builder<NamedPerson> {

  FullNamePersonBuilder() {
    super(NamedPerson::new);
    addIntSetter("age", NamedPerson::setAge);
    addBuilder("name", NamedPerson::setName, new NameBuilder());
  }

}
