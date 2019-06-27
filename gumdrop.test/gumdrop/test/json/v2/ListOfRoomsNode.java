package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.CollectionNode;

import java.util.ArrayList;
import java.util.List;

class ListOfRoomsNode extends CollectionNode<List<Room>> {

  ListOfRoomsNode() {
    super(new ArrayList<>(), new Binding<>(RoomNode::new, List::add));
  }

}
