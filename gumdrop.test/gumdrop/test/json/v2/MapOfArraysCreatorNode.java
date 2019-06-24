package gumdrop.test.json.v2;

import gumdrop.json.v2.KeyedBoundCreatorNode;
import gumdrop.json.v2.TriBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MapOfArraysCreatorNode extends KeyedBoundCreatorNode<Map<String, List<String>>> {

  MapOfArraysCreatorNode() {
    super(new HashMap<>(), new TriBinding<>(StringListUniCreatorNode::new, Map::put));
  }

}
