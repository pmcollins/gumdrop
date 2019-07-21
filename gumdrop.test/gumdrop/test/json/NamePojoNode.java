package gumdrop.test.json;

import gumdrop.json.FieldBinding;
import gumdrop.json.PojoNode;
import gumdrop.json.StringNode;
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
