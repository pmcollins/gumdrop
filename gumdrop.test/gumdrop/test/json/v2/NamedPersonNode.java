package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;
import gumdrop.json.v2.StringAcceptorNode;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;

class NamedPersonNode extends Node<NamedPerson> {

  NamedPersonNode() {
    super(new NamedPerson());
  }

  @Override
  public Chainable next(String key) {
    if ("name".equals(key)) {
      NameNode nameNode = new NameNode();
      Name name = nameNode.instance();
      instance().setName(name);
      return nameNode;
    } else if ("age".equals(key)) {
      return new StringAcceptorNode<>(
        instance(),
        (p, s) -> p.setAge(Integer.parseInt(s))
      );
    }
    return null;
  }

}
