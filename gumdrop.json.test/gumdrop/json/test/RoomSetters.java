package gumdrop.json.test;

import gumdrop.json.Setters;

class RoomSetters extends Setters<Room> {

  RoomSetters() {
    super(Room::new);
    addSetter("name", Room::setName);
    addMember("people", Room::setPeople, new FullNamePersonListSetters());
  }

}
