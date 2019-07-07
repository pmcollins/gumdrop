package gumdrop.test.json.v2;

import gumdrop.json.v2.*;
import gumdrop.test.fake.NamedPerson;

class NamedPersonNode extends Node<NamedPerson> {

  NamedPersonNode() {
    super(new NamedPerson());
  }

  @Override
  public Chainable next(String key) {
    if ("name".equals(key)) {
      return new NullableNode<>(new NameNode(), instance(), NamedPerson::setName);
    } else if ("age".equals(key)) {
      return new StringAcceptorNode<>(instance(), (p, s) -> p.setAge(Integer.parseInt(s)));
    }
    return null;
  }

}
