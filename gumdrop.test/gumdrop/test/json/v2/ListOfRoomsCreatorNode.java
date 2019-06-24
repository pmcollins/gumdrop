package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionCreatorNode;

import java.util.ArrayList;
import java.util.List;

class ListOfRoomsCreatorNode extends CollectionCreatorNode<List<Room>> {

  ListOfRoomsCreatorNode() {
    super(new ArrayList<>(), new Binding<>(RoomCreatorNode::new, List::add));
  }

}
