package gumdrop.test.json.v2;

import gumdrop.json.v2.StringDictionaryNode;

import java.util.HashMap;
import java.util.Map;

class StringMapNode extends StringDictionaryNode<Map<String, String>> {

  StringMapNode() {
    super(new HashMap<>(), Map::put);
  }

}
