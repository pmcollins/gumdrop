package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;
import gumdrop.json.StringDeserializer;
import gumdrop.test.fake.Name;

class PersonAttributesDeserializer extends Deserializer<Name> {

  PersonAttributesDeserializer(Name name) {
    super(name);
  }

  @Override
  public Chainable next(String key) {
    return new StringDeserializer(s -> {
      if ("first".equals(key)) {
        getValue().setFirst(s);
      } else if ("last".equals(key)) {
        getValue().setLast(s);
      }
    });
  }

}
