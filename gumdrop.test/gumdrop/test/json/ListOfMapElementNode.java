package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Node;

import java.util.List;
import java.util.Map;

class ListOfMapElementNode extends Node<List<Map<String, Integer>>> {

  ListOfMapElementNode(List<Map<String, Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new StringIntMapNode(map -> getValue().add(map));
  }

}
