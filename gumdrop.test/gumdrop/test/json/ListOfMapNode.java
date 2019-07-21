package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.Node;

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
