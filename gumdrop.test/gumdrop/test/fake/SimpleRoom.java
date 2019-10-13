package gumdrop.test.fake;

import java.util.ArrayList;
import java.util.List;

public class SimpleRoom {

  private final List<SimplePerson> people = new ArrayList<>();

  public void add(SimplePerson person) {
    people.add(person);
  }

  public List<SimplePerson> getPeople() {
    return people;
  }

}
