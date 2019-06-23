package gumdrop.test.json2;

import gumdrop.json2.KeyedCreatorNode;

import java.util.HashMap;
import java.util.Map;

class StringMapKeyedCreatorNode extends KeyedCreatorNode<Map<String, String>> {

  StringMapKeyedCreatorNode() {
    super(new HashMap<>(), Map::put);
  }

}
