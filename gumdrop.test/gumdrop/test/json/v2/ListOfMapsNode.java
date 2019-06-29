package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ListOfMapsNode extends ArrayNode<List<Map<String, String>>> {

  ListOfMapsNode() {
    super(
      new ArrayList<>(),
      new Binding<>(List::add, StringMapNode::new)
    );
  }

}
