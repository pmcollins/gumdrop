package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.IntNode;
import gumdrop.json.v2.Node;
import gumdrop.json.v2.UnknownKeyException;
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
