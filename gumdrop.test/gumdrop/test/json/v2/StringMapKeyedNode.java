package gumdrop.test.json.v2;

import gumdrop.json.v2.KeyedNode;

import java.util.HashMap;
import java.util.Map;

class StringMapKeyedNode extends KeyedNode<Map<String, String>> {

  StringMapKeyedNode() {
    super(new HashMap<>(), Map::put);
  }

}
