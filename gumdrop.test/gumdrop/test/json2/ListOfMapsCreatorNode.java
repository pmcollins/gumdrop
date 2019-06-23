package gumdrop.test.json2;

import gumdrop.json2.Binding;
import gumdrop.json2.CollectionCreatorNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ListOfMapsCreatorNode extends CollectionCreatorNode<List<Map<String, String>>> {

  ListOfMapsCreatorNode() {
    super(new ArrayList<>(), new Binding<>(StringMapKeyedCreatorNode::new, List::add));
  }

}
