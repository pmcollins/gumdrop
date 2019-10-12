package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.NamedPerson;

class NamedPersonDeserializer extends NullableDeserializer<NamedPerson> {

  @Override
  public Chainable next() {
    NamedPerson namedPerson = new NamedPerson();
    setValue(namedPerson);
    return new NamedPersonAttributesDeserializer(namedPerson);
  }

}
