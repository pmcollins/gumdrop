package gumdrop.test.json2;

import gumdrop.json2.Binding;
import gumdrop.json2.CollectionCreatorNode;

import java.util.ArrayList;
import java.util.List;

class ListOfRoomsCreatorNode extends CollectionCreatorNode<List<Room>> {

  ListOfRoomsCreatorNode() {
    super(new ArrayList<>(), new Binding<>(RoomCreatorNode::new, List::add));
  }

}
