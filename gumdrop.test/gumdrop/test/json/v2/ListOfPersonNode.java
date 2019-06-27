package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionNode;

import java.util.ArrayList;
import java.util.List;

class ListOfPersonNode extends CollectionNode<List<Person>> {

  ListOfPersonNode() {
    super(new ArrayList<>(), new Binding<>(PersonNode::new, List::add));
  }

}
