package gumdrop.test.json.v2;

import gumdrop.json.v2.TriBindingNode;
import gumdrop.json.v2.TriBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MapOfArraysNode extends TriBindingNode<Map<String, List<String>>> {

  MapOfArraysNode() {
    super(new HashMap<>(), new TriBinding<>(StringListUniNode::new, Map::put));
  }

}
