package gumdrop.test.json;

import java.util.ArrayList;
import java.util.List;

class Room {

  private String name;
  private List<FullNamePerson> people = new ArrayList<>();

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  List<FullNamePerson> getPeople() {
    return people;
  }

  void setPeople(List<FullNamePerson> people) {
    this.people = people;
  }

  @Override
  public String toString() {
    return "Room{name='" + name + '\'' + ", people=" + people + '}';
  }

}
