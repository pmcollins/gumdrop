package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Node;
import gumdrop.json.StringNode;
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
