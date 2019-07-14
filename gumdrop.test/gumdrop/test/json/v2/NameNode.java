package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;
import gumdrop.test.fake.Name;

import java.util.function.Consumer;

class NameNode extends NullableNode<Name> {

  NameNode(Consumer<Name> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    Name name = new Name();
    setValue(name);
    return new NameAttributesNode(name);
  }

}
