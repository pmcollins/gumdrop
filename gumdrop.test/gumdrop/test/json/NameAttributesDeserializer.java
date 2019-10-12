package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;
import gumdrop.json.StringDeserializer;
import gumdrop.json.UnknownKeyException;
import gumdrop.test.fake.Name;

class NameAttributesDeserializer extends Deserializer<Name> {

  NameAttributesDeserializer(Name name) {
    super(name);
  }

  @Override
  public Chainable next(String key) {
    if ("first".equals(key)) {
      return new StringDeserializer(s -> getValue().setFirst(s));
    } else if ("last".equals(key)) {
      return new StringDeserializer(s -> getValue().setLast(s));
    }
    throw new UnknownKeyException(key);
  }

}
