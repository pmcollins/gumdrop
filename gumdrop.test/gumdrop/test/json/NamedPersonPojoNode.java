package gumdrop.test.json;

import gumdrop.json.FieldBinding;
import gumdrop.json.IntNode;
import gumdrop.json.PojoNode;
import gumdrop.test.fake.NamedPerson;

import java.util.List;

class NamedPersonPojoNode extends PojoNode<NamedPerson> {

  NamedPersonPojoNode() {
    super(NamedPerson::new, List.of(
      new FieldBinding<>("age", NamedPerson::setAge, IntNode::new),
      new FieldBinding<>("name", NamedPerson::setName, NamePojoNode::new)
    ));
  }

}
