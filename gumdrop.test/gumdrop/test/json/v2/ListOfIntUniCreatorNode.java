package gumdrop.test.json.v2;

import gumdrop.json.v2.UniCreatorNode;

import java.util.ArrayList;
import java.util.List;

class ListOfIntUniCreatorNode extends UniCreatorNode<List<Integer>> {

  ListOfIntUniCreatorNode() {
    super(
      new ArrayList<>(),
      (list, s) -> list.add(Integer.valueOf(s))
    );
  }

}
