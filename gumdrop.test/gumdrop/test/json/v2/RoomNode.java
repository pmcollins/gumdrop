package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;

class RoomNode extends ArrayNode<Room> {

  RoomNode() {
    super(new Room(), new Binding<>(Room::add, PersonNode::new));
  }

}
