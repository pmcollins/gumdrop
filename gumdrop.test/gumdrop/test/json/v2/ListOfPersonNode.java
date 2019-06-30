package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;
import gumdrop.test.fake.Person;

import java.util.ArrayList;
import java.util.List;

class ListOfPersonNode extends ArrayNode<List<Person>> {

  ListOfPersonNode() {
    super(new ArrayList<>(), new Binding<>(List::add, PersonNode::new));
  }

}
