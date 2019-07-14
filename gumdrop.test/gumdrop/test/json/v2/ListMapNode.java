package gumdrop.test.json.v2;

import gumdrop.json.v2.Chainable;
import gumdrop.json.v2.NullableNode;

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
