package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;

import java.util.List;
import java.util.Map;

class ListMapElementNode extends Node<Map<String, List<Integer>>> {

  ListMapElementNode(Map<String, List<Integer>> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntListNode(list -> getValue().put(key, list));
  }

}
