package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;
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
