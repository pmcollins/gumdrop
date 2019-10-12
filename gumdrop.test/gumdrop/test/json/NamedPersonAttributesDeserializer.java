package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntDeserializer;
import gumdrop.json.Deserializer;
import gumdrop.json.UnknownKeyException;
import gumdrop.test.fake.NamedPerson;

class NamedPersonAttributesDeserializer extends Deserializer<NamedPerson> {

  NamedPersonAttributesDeserializer(NamedPerson namedPerson) {
    super(namedPerson);
  }

  @Override
  public Chainable next(String key) {
    if ("age".equals(key)) {
      return new IntDeserializer(age -> getValue().setAge(age));
    } else if ("name".equals(key)) {
      return new NameDeserializer(name -> getValue().setName(name));
    }
    throw new UnknownKeyException(key);
  }

}
