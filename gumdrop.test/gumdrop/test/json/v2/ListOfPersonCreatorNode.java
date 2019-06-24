package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionCreatorNode;

import java.util.ArrayList;
import java.util.List;

class ListOfPersonCreatorNode extends CollectionCreatorNode<List<Person>> {

  ListOfPersonCreatorNode() {
    super(new ArrayList<>(), new Binding<>(PersonCreatorNode::new, List::add));
  }

}
