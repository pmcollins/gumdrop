package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.IntNode;
import gumdrop.json.v2.Node;

import java.util.Map;

class StringIntMapElementNode extends Node<Map<String, Integer>> {

  StringIntMapElementNode(Map<String, Integer> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntNode(i -> getValue().put(key, i));
  }

}
