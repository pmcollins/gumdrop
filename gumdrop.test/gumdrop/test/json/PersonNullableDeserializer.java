package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.SimplePerson;

class PersonNullableDeserializer extends NullableDeserializer<SimplePerson> {

  @Override
  public Chainable next() {
    SimplePerson person = new SimplePerson();
    setValue(person);
    return new PersonAttributesDeserializer(person);
  }

}
