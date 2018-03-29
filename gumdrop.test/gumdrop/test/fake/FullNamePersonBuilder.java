package gumdrop.test.fake;

import gumdrop.common.builder.Builder;

class FullNamePersonBuilder extends Builder<Person> {

  FullNamePersonBuilder() {
    super(Person::new);
    addIntSetter("age", Person::setAge);
    addMember("name", Person::setName, new NameBuilder());
  }

}
