package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionCreatorNode;

class RoomCreatorNode extends CollectionCreatorNode<Room> {

  RoomCreatorNode() {
    super(new Room(), new Binding<>(PersonCreatorNode::new, Room::add));
  }

}
