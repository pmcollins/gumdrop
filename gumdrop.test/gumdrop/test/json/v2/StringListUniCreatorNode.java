package gumdrop.test.json.v2;

import gumdrop.json.v2.UniCreatorNode;

import java.util.ArrayList;
import java.util.List;

class StringListUniCreatorNode extends UniCreatorNode<List<String>> {

  StringListUniCreatorNode() {
    super(new ArrayList<>(), List::add);
  }

}
