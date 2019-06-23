package gumdrop.test.json2;

import java.util.ArrayList;
import java.util.List;

public class Room {

  private final List<Person> people = new ArrayList<>();

  public void add(Person person) {
    people.add(person);
  }

  public List<Person> getPeople() {
    return people;
  }

}
