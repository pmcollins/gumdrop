package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;
import gumdrop.test.fake.Person;

class PersonNode extends NullableNode<Person> {

  @Override
  public Chainable next() {
    Person person = new Person();
    setValue(person);
    return new PersonAttributesNode(person);
  }

}
