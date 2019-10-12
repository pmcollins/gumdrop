package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Deserializer;
import gumdrop.json.StringDeserializer;
import gumdrop.test.fake.Person;

class PersonAttributesDeserializer extends Deserializer<Person> {

  PersonAttributesDeserializer(Person person) {
    super(person);
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
