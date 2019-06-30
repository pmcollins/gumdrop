package gumdrop.test.json.v2;

import gumdrop.json.v2.ArrayLeafNode;

import java.util.ArrayList;
import java.util.List;

class IntListLeafNode extends ArrayLeafNode<List<Integer>> {

  IntListLeafNode() {
    super(new ArrayList<>(), (list, s) -> list.add(Integer.valueOf(s)));
  }

}
