package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.IntNode;
import gumdrop.json.Node;

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
