package gumdrop.test.json2;

import gumdrop.json2.Binding;
import gumdrop.json2.CollectionCreatorNode;

class RoomCreatorNode extends CollectionCreatorNode<Room> {

  RoomCreatorNode() {
    super(new Room(), new Binding<>(PersonCreatorNode::new, Room::add));
  }

}
