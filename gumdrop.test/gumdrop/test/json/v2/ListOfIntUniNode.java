package gumdrop.test.json.v2;

import gumdrop.json.v2.UniNode;

import java.util.ArrayList;
import java.util.List;

class ListOfIntUniNode extends UniNode<List<Integer>> {

  ListOfIntUniNode() {
    super(
      new ArrayList<>(),
      (list, s) -> list.add(Integer.valueOf(s))
    );
  }

}
