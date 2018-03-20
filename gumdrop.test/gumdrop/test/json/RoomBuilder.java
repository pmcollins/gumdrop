package gumdrop.test.json;

import gumdrop.common.builder.Builder;
import gumdrop.test.pojo.Room;

public class RoomBuilder extends Builder<Room> {

  public RoomBuilder() {
    super(Room::new);
    addSetter("name", Room::setName);
    addMember("people", Room::setPeople, new FullNamePersonListBuilder());
  }

}
