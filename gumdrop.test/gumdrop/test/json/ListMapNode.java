package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListMapNode extends NullableNode<Map<String, List<Integer>>> {

  @Override
  public Chainable next() {
    Map<String, List<Integer>> map = new HashMap<>();
    setValue(map);
    return new ListMapElementNode(map);
  }

}
