package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;
import gumdrop.test.fake.Person;

class PersonNode extends NullableNode<Person> {

  @Override
  public Chainable next() {
    Person person = new Person();
    setValue(person);
    return new PersonAttributesNode(person);
  }

}
