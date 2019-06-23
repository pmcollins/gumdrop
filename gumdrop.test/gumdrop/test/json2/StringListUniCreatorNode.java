package gumdrop.test.json2;

import gumdrop.json2.UniCreatorNode;

import java.util.ArrayList;
import java.util.List;

class StringListUniCreatorNode extends UniCreatorNode<List<String>> {

  StringListUniCreatorNode() {
    super(new ArrayList<>(), List::add);
  }

}
