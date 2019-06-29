package gumdrop.test.json.v2;

import gumdrop.json.v2.StringArrayNode;

import java.util.ArrayList;
import java.util.List;

class StringArrayListNode extends StringArrayNode<List<String>> {

  StringArrayListNode() {
    super(new ArrayList<>(), List::add);
  }

}
