package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;
import gumdrop.test.fake.NamedPerson;

class NamedPersonNode extends NullableNode<NamedPerson> {

  @Override
  public Chainable next() {
    NamedPerson namedPerson = new NamedPerson();
    setValue(namedPerson);
    return new NamedPersonAttributesNode(namedPerson);
  }

}
