package gumdrop.test.fake;

import gumdrop.test.fake.Person;

import java.util.ArrayList;
import java.util.List;

public class SimpleRoom {

  private final List<Person> people = new ArrayList<>();

  public void add(Person person) {
    people.add(person);
  }

  public List<Person> getPeople() {
    return people;
  }

}
