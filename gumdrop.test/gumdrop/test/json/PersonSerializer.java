package gumdrop.test.json;

import gumdrop.json.print.IntSerializer;
import gumdrop.json.print.ObjectSerializer;
import gumdrop.test.fake.Person;

class PersonSerializer extends ObjectSerializer<Person> {

  PersonSerializer() {
    addMethodSerializer("age", Person::getAge, new IntSerializer());
    addMethodSerializer("name", Person::getName, new NameSerializer());
  }

}
