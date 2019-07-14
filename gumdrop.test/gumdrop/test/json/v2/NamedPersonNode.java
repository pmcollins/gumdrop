package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;
import gumdrop.test.fake.NamedPerson;

class NamedPersonNode extends NullableNode<NamedPerson> {

  @Override
  public Chainable next() {
    NamedPerson namedPerson = new NamedPerson();
    setValue(namedPerson);
    return new NamedPersonAttributesNode(namedPerson);
  }

}
