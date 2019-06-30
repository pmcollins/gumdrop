package gumdrop.test.json.v2;

import gumdrop.json.v2.DictionaryNode;
import gumdrop.json.v2.DictionaryBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MapOfArraysNode extends DictionaryNode<Map<String, List<String>>> {

  MapOfArraysNode() {
    super(new HashMap<>(), new DictionaryBinding<>(new StringArrayListNodeFactory(), Map::put));
  }

}
