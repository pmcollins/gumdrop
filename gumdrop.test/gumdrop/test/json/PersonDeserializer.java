package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.Person;

class PersonDeserializer extends NullableDeserializer<Person> {

  @Override
  public Chainable next() {
    Person person = new Person();
    setValue(person);
    return new PersonAttributesDeserializer(person);
  }

}
