package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;
import gumdrop.json.v2.StringNode;
import gumdrop.test.fake.Person;

class PersonAttributesNode extends Node<Person> {

  PersonAttributesNode(Person person) {
    super(person);
  }

  @Override
  public Chainable next(String key) {
    return new StringNode(s -> {
      if ("first".equals(key)) {
        getValue().setFirst(s);
      } else if ("last".equals(key)) {
        getValue().setLast(s);
      }
    });
  }

}
