package gumdrop.test.json;

import gumdrop.json.FieldBinding;
import gumdrop.json.IntDeserializer;
import gumdrop.json.ObjectDeserializer;
import gumdrop.test.fake.Person;

import java.util.List;

class PersonDeserializer extends ObjectDeserializer<Person> {

  PersonDeserializer() {
    super(Person::new, List.of(
      new FieldBinding<>("age", Person::setAge, IntDeserializer::new),
      new FieldBinding<>("name", Person::setName, NameDeserializer::new)
    ));
  }

}
