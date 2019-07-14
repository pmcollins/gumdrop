package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ListOfMapNode extends Node<List<Map<String, Integer>>> {

  @Override
  public Chainable next() {
    ArrayList<Map<String, Integer>> list = new ArrayList<>();
    setValue(list);
    return new ListOfMapElementNode(list);
  }

}
