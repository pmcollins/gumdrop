package gumdrop.test.json.v2;

import gumdrop.json.v2.StringArrayNode;

import java.util.ArrayList;
import java.util.List;

class IntListNode extends StringArrayNode<List<Integer>> {

  IntListNode() {
    super(new ArrayList<>(), (list, s) -> list.add(Integer.valueOf(s)));
  }

}
