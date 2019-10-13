package gumdrop.test.fake;

import java.util.ArrayList;
import java.util.List;

public class Room {

  private String name;
  private List<Person> people = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Person> getPeople() {
    return people;
  }

  public void setPeople(List<Person> people) {
    this.people = people;
  }

  @Override
  public String toString() {
    return "Room{name='" + name + '\'' + ", people=" + people + '}';
  }

}
