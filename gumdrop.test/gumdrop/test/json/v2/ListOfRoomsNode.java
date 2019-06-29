package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;

import java.util.ArrayList;
import java.util.List;

class ListOfRoomsNode extends ArrayNode<List<Room>> {

  ListOfRoomsNode() {
    super(new ArrayList<>(), new Binding<>(List::add, RoomNode::new));
  }

}
