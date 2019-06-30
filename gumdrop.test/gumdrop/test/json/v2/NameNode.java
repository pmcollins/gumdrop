package gumdrop.test.json.v2;

import gumdrop.json.v2.SimplePojoNode;
import gumdrop.test.fake.Name;

import java.util.function.BiConsumer;

class NameNode extends SimplePojoNode<Name> {

  NameNode() {
    super(new Name(), NameNode::setterMapping);
  }

  private static BiConsumer<Name, String> setterMapping(String key) {
    if ("first".equals(key)) {
      return Name::setFirst;
    } else if ("last".equals(key)) {
      return Name::setLast;
    }
    return null;
  }

}
