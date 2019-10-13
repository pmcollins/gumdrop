package gumdrop.test.fake;

import java.util.ArrayList;
import java.util.List;

public class SimpleRoom {

  private final List<Name> people = new ArrayList<>();

  public void add(Name person) {
    people.add(person);
  }

  public List<Name> getNames() {
    return people;
  }

}
