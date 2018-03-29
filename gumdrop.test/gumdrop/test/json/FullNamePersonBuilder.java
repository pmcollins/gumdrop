package gumdrop.test.json;

import gumdrop.common.builder.Builder;
import gumdrop.test.pojo.Person;

class FullNamePersonBuilder extends Builder<Person> {

  FullNamePersonBuilder() {
    super(Person::new);
    addIntSetter("age", Person::setAge);
    addMember("name", Person::setName, new NameBuilder());
  }

}
