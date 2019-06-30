package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;
import gumdrop.test.fake.SimpleRoom;

class SimpleRoomNode extends ArrayNode<SimpleRoom> {

  SimpleRoomNode() {
    super(new SimpleRoom(), new Binding<>(SimpleRoom::add, PersonNode::new));
  }

}
