package gumdrop.test.fake;

import gumdrop.common.builder.v1.Builder;

public class RoomBuilder extends Builder<Room> {

  public RoomBuilder() {
    super(Room::new);
    addSetter("name", Room::setName);
    addBuilder("people", Room::setPeople, new FullNamePersonListBuilder());
  }

}
