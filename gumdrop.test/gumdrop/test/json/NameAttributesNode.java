package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Node;
import gumdrop.json.StringNode;
import gumdrop.json.UnknownKeyException;
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
