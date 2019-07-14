package gumdrop.test.json.v2;

import gumdrop.json.v2.FieldBinding;
import gumdrop.json.v2.IntNode;
import gumdrop.json.v2.PojoNode;
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
