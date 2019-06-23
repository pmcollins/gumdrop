package gumdrop.test.json2;

import gumdrop.json2.Binding;
import gumdrop.json2.CollectionCreatorNode;

import java.util.ArrayList;
import java.util.List;

class ListOfPersonCreatorNode extends CollectionCreatorNode<List<Person>> {

  ListOfPersonCreatorNode() {
    super(new ArrayList<>(), new Binding<>(PersonCreatorNode::new, List::add));
  }

}
