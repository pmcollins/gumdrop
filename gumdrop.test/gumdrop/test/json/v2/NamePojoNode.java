package gumdrop.test.json.v2;

import gumdrop.json.v2.FieldBinding;
import gumdrop.json.v2.PojoNode;
import gumdrop.json.v2.StringNode;
import gumdrop.test.fake.Name;

import java.util.List;
import java.util.function.Consumer;

class NamePojoNode extends PojoNode<Name> {

  NamePojoNode() {
    this(null);
  }

  NamePojoNode(Consumer<Name> listener) {
    super(Name::new, List.of(
      new FieldBinding<>("first", Name::setFirst, StringNode::new),
      new FieldBinding<>("last", Name::setLast, StringNode::new)
    ), listener);
  }

}
