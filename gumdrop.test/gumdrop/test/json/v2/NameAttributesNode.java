package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;
import gumdrop.json.v2.StringNode;
import gumdrop.json.v2.UnknownKeyException;
import gumdrop.test.fake.Name;

class NameAttributesNode extends Node<Name> {

  NameAttributesNode(Name name) {
    super(name);
  }

  @Override
  public Chainable next(String key) {
    if ("first".equals(key)) {
      return new StringNode(s -> getValue().setFirst(s));
    } else if ("last".equals(key)) {
      return new StringNode(s -> getValue().setLast(s));
    }
    throw new UnknownKeyException(key);
  }

}
