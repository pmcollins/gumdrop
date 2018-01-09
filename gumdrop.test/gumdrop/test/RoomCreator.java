package gumdrop.test;

import gumdrop.common.Creator;
import gumdrop.test.pojo.Room;

public class RoomCreator extends Creator<Room> {

  public RoomCreator() {
    super(Room::new);
    addSetter("name", Room::setName);
    addMember("people", Room::setPeople, new FullNamePersonListCreator());
  }

}
