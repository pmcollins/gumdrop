package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntNode;
import gumdrop.json.Node;
import gumdrop.json.UnknownKeyException;
import gumdrop.test.fake.NamedPerson;

class NamedPersonAttributesNode extends Node<NamedPerson> {

  NamedPersonAttributesNode(NamedPerson namedPerson) {
    super(namedPerson);
  }

  @Override
  public Chainable next(String key) {
    if ("age".equals(key)) {
      return new IntNode(age -> getValue().setAge(age));
    } else if ("name".equals(key)) {
      return new NameNode(name -> getValue().setName(name));
    }
    throw new UnknownKeyException(key);
  }

}
