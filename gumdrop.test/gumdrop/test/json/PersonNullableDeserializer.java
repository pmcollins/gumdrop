package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.Name;

class PersonNullableDeserializer extends NullableDeserializer<Name> {

  @Override
  public Chainable next() {
    Name name = new Name();
    setValue(name);
    return new PersonAttributesDeserializer(name);
  }

}
