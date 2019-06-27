package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionNode;

class RoomNode extends CollectionNode<Room> {

  RoomNode() {
    super(new Room(), new Binding<>(PersonNode::new, Room::add));
  }

}
