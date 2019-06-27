package gumdrop.test.json.v2;

import gumdrop.json.v2.UniNode;

import java.util.ArrayList;
import java.util.List;

class StringListUniNode extends UniNode<List<String>> {

  StringListUniNode() {
    super(new ArrayList<>(), List::add);
  }

}
