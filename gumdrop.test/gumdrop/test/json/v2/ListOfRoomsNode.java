package gumdrop.test.json.v2;

import gumdrop.json.v2.Binding;
import gumdrop.json.v2.ArrayNode;
import gumdrop.test.fake.SimpleRoom;

import java.util.ArrayList;
import java.util.List;

class ListOfRoomsNode extends ArrayNode<List<SimpleRoom>> {

  ListOfRoomsNode() {
    super(new ArrayList<>(), new Binding<>(List::add, SimpleRoomNode::new));
  }

}
